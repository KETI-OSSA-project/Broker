package com.example.broker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.example.broker.databinding.ActivityMainBinding;
import com.example.broker.service.MqttService;
import com.example.broker.utils.AppPreferences;
import com.example.broker.utils.NetworkCallBack;
import com.example.broker.utils.Utils;

// 설정 변경 사항을 자동으로 감지하고 반영
public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> requestNotificationPermissionIntentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // AppPreferences 초기화
        AppPreferences.init(getApplicationContext());

        if (AppPreferences.getFirstRun()) {
            initSharedPrefs();
            Log.d(TAG, "onCreate: its first time run");
        }

        setIP();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        requestNotificationPermissionIntentLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Log.d(TAG, "notification permission granted");
                } else {
                    Log.w(TAG, "notification permission not granted");
                    showSnackBar("Please grant Notification permission from App Settings");
                }
            });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        } else {
            setTitle(savedInstanceState.getCharSequence(TAG));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForInAppUpdate();
    }

    private void checkForInAppUpdate() {
        var updateManager = AppUpdateManagerFactory.create(this);
        var appUpdateInfoTask = updateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    updateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                        0
                    );
                } catch (Exception e) {
                    Log.e(TAG, "checkForInAppUpdate: ", e);
                }
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SharedPreferences mPreferences;
        private EditTextPreference host;
        private EditTextPreference port;
        private EditTextPreference username;
        private EditTextPreference password;
        private SwitchPreference brokerTurnOrOff;
        private final String TAG = "SettingsFragment";

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPreferences = requireActivity().getSharedPreferences("MQTT Broker", MODE_PRIVATE);
            host = findPreference(getString(R.string.mqtt_host));
            port = findPreference(getString(R.string.mqtt_port));
            username = findPreference(getString(R.string.mqtt_username));
            password = findPreference(getString(R.string.mqtt_password));
            brokerTurnOrOff = findPreference(getString(R.string.mqtt_broker_status));

            ConnectivityManager connectivityManager =
                (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new NetworkCallBack());

            ContextCompat.registerReceiver(
                requireContext(),
                mqttReceiver,
                new IntentFilter(Utils.MQTT_STATUS_ON_OR_OFF),
                ContextCompat.RECEIVER_NOT_EXPORTED
            );
            ContextCompat.registerReceiver(
                requireContext(),
                networkReceiver,
                new IntentFilter(Utils.NETWORK_BROADCAST_ACTION),
                ContextCompat.RECEIVER_NOT_EXPORTED
            );
        }

        private final BroadcastReceiver networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getExtras() != null) {
                    Boolean status = intent.getExtras().getBoolean("status");
                    if (status) {
                        Log.d(TAG, "onReceive: ");
                    } else {
                        if (host != null) host.setText("Not Set");
                        if (brokerTurnOrOff != null) brokerTurnOrOff.setChecked(false);
                        ((MainActivity) getActivity()).stopService();
                    }
                }
            }
        };

        private final BroadcastReceiver mqttReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getExtras() != null) {
                    boolean status = intent.getExtras().getBoolean("status");
                    Log.i(TAG, "onReceive: " + status);
                    if (status) {
                        if (host != null) host.setText(intent.getExtras().getString("host"));
                        if (brokerTurnOrOff != null) brokerTurnOrOff.setChecked(true);
                    } else {
                        if (brokerTurnOrOff != null) brokerTurnOrOff.setChecked(false);
                        if (host != null) host.setText("Not Set");
                        AppPreferences.setMqttBrokerStatus(false);
                    }
                }
            }
        };

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onResume() {
            super.onResume();
            if (host != null) {
                host.setText(AppPreferences.getMqttHost());
                port.setText(AppPreferences.getMqttPort());
                password.setText(AppPreferences.getMqttPassword());
                username.setText(AppPreferences.getMqttUserName());
                brokerTurnOrOff.setChecked(AppPreferences.getMqttBrokerStatus());

                if (AppPreferences.getMqttBrokerStatus() &&
                    !Utils.isMyServiceRunning(requireContext(), MqttService.class)) {
                    ((MainActivity) getActivity()).startService();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "Preference changed. " + key);
        try {
            if (key != null) {
                if (key.equals(getString(R.string.mqtt_broker_status))) {
                    Log.d(TAG, "Server status changed");
                    boolean status = sharedPreferences.getBoolean(key, false);
                    Log.d(TAG, "Start Server?" + status);
                    Intent broadcastIntent = new Intent(Utils.MQTT_STATUS_ON_OR_OFF);
                    broadcastIntent.setPackage(getPackageName());

                    if (status) {
                        boolean isWifiConnected = Utils.isConnectedToWifi(this);
                        Log.d(TAG, "onSharedPreferenceChanged: " + isWifiConnected);
                        if (isWifiConnected) {
                            broadcastIntent.putExtra("host", Utils.getIPAddress(true));
                            broadcastIntent.putExtra("status", true);
                            AppPreferences.setMqttBrokerStatus(true);
                            AppPreferences.setAllowLocalRun(false);
                            Log.d(TAG, "Starting Server");
                            startService();
                        } else {
                            broadcastIntent.putExtra("status", true);
                            broadcastIntent.putExtra("host", "127.0.0.1");
                            AppPreferences.setMqttBrokerStatus(true);
                            AppPreferences.setAllowLocalRun(true);
                            Log.d(TAG, "Starting Server");
                            startService();
                        }
                    } else {
                        AppPreferences.setAllowLocalRun(false);
                        AppPreferences.setMqttHost("");
                        broadcastIntent.putExtra("status", false);
                        AppPreferences.setMqttBrokerStatus(false);
                        Log.d(TAG, "Stopping Server");
                        stopService();
                    }
                    sendBroadcast(broadcastIntent);
                } else if (key.equals(getString(R.string.mqtt_password)) ||
                          key.equals(getString(R.string.mqtt_username)) ||
                          key.equals(getString(R.string.mqtt_port))) {

                    if (key.equals(getString(R.string.mqtt_password))) {
                        AppPreferences.setMqttPassword(sharedPreferences.getString(key, ""));
                    }
                    if (key.equals(getString(R.string.mqtt_username))) {
                        AppPreferences.setMqttUserName(sharedPreferences.getString(key, ""));
                    }
                    if (key.equals(getString(R.string.mqtt_port))) {
                        AppPreferences.setMqttPort(sharedPreferences.getString(key, ""));
                    }
                    showSnackBar("You need to restart the server for applying the config changes");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "onSharedPreferenceChanged: ", e);
        }
    }

    private void initSharedPrefs() {
        AppPreferences.setMqttHost(Utils.getIPAddress(true));
        AppPreferences.setMqttPort("1883");
        AppPreferences.setMqttUserName("admin");
        AppPreferences.setMqttPassword(Utils.generatePassword());
        AppPreferences.setFirstRun(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startService() {
        Log.d(TAG, "Starting MQTT Service");

        // Sets up permissions request launcher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    requestNotificationPermissionIntentLauncher.launch(intent);
                } catch (Exception ex) {
                    Log.e(TAG, "onCreate: ", ex);
                    showSnackBar(ex.getLocalizedMessage() != null ?
                        ex.getLocalizedMessage() : "Unknown error occurred when requesting permission");
                }
                return;
            }
        }

        Intent serviceIntent = new Intent(this, MqttService.class);
        if (Utils.isMyServiceRunning(this, MqttService.class)) {
            stopService(serviceIntent);
        }
        if (AppPreferences.getMqttBrokerStatus()) {
            startForegroundService(serviceIntent);
        }
    }

    private void showSnackBar(String message) {
        View contextView = findViewById(android.R.id.content);
        Snackbar.make(contextView, message, Snackbar.LENGTH_LONG).show();
    }

    private void stopService() {
        try {
            Log.d(TAG, "Stopping MQTT Service");
            Intent serviceIntent = new Intent(this, MqttService.class);
            stopService(serviceIntent);
        } catch (Exception ex) {
            Log.e(TAG, "stopService: ", ex);
        }
    }

    private void setIP() {
        AppPreferences.setMqttHost(Utils.getIPAddress(true));
    }
}