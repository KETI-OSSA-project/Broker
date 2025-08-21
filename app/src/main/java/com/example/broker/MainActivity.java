package com.example.broker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    private static final String TAG = "BrokerActivity";

    private TextView statusText;
    private TextView logText;
    private Button clearLogButton;

    private MessagingService messagingService; // (현재 활용 X, 추후 확장 대비)
    private boolean isServiceBound = false;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupListeners();

        // MessagingService 시작 및 바인딩
        startAndBindService();
    }

    private void initViews() {
        // XML 레이아웃에서 뷰들을 찾아서 초기화
        statusText = findViewById(R.id.statusText);
        logText = findViewById(R.id.logText);
        clearLogButton = findViewById(R.id.clearLogButton);
    }

    private void setupListeners() {
        clearLogButton.setOnClickListener(v -> {
            logText.setText("");
            addLog("로그가 지워졌습니다");
        });
    }

    private void startAndBindService() {
        Intent serviceIntent = new Intent(this, MessagingService.class);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isServiceBound = true;
            statusText.setText("🟢 실행 중");
            addLog("Android broker 연결됨");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
            statusText.setText("🔴 연결 끊김");
            addLog("Android broker 연결 해제됨");
        }
    };

    private void addLog(String message) {
        String timestamp = timeFormat.format(new Date());
        String logEntry = "[" + timestamp + "] " + message + "\n";

        runOnUiThread(() -> {
            logText.append(logEntry);
            final int scrollAmount = logText.getLayout() != null ?
                logText.getLayout().getLineTop(logText.getLineCount()) - logText.getHeight() : 0;
            if (scrollAmount > 0) {
                logText.scrollTo(0, scrollAmount);
            }
        });

        Log.d(TAG, message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }
}
