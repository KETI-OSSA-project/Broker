package com.example.broker;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * MainActivity - 브로커 상태 모니터링 UI
 */
public class MainActivity extends Activity {

    private static final String TAG = "BrokerActivity";

    private TextView statusText;
    private TextView clientCountText;
    private TextView topicCountText;
    private TextView logText;
    private Button clearLogButton;
    private Button refreshButton;

    private MessagingService messagingService;
    private boolean isServiceBound = false;
    private Handler uiHandler;

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupListeners();
        uiHandler = new Handler(Looper.getMainLooper());

        // MessagingService 시작 및 바인딩
        startAndBindService();
        startUIUpdates();
        addLog("브로커 UI 시작됨");
    }

    private void initViews() {
        // XML 레이아웃에서 뷰들을 찾아서 초기화
        statusText = findViewById(R.id.statusText);
        clientCountText = findViewById(R.id.clientCountText);
        topicCountText = findViewById(R.id.topicCountText);
        logText = findViewById(R.id.logText);
        clearLogButton = findViewById(R.id.clearLogButton);
        refreshButton = findViewById(R.id.refreshButton);
    }

    private void setupListeners() {
        clearLogButton.setOnClickListener(v -> {
            logText.setText("");
            addLog("로그가 지워졌습니다");
        });

        refreshButton.setOnClickListener(v -> {
            updateBrokerStats();
            addLog("상태 새로고침됨");
        });
    }

    private void startAndBindService() {
        Intent serviceIntent = new Intent(this, MessagingService.class);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        addLog("Android broker 시작 중...");
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "✅ MessagingService 연결됨");
            isServiceBound = true;
            statusText.setText("🟢 실행 중");
            addLog("Android broker 연결됨"); // UI용
            updateBrokerStats();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "❌ MessagingService 연결 해제됨");
            isServiceBound = false;
            statusText.setText("🔴 연결 끊김");
            addLog("Android broker 연결 해제됨");
        }
    };

    private void startUIUpdates() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                updateBrokerStats();
                uiHandler.postDelayed(this, 5000);
            }
        });
    }

    private void updateBrokerStats() {
        if (isServiceBound) {
            // 실제 서비스에서 통계 가져오기 (임시로 랜덤 값 사용) -> 추후 수정
            int clients = (int) (Math.random() * 10);
            int topics = (int) (Math.random() * 5);

            clientCountText.setText(String.valueOf(clients));
            topicCountText.setText(String.valueOf(topics));
        } else {
            clientCountText.setText("0");
            topicCountText.setText("0");
        }
    }

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
        if (uiHandler != null) {
            uiHandler.removeCallbacksAndMessages(null);
        }
        addLog("브로커 UI 종료됨");
    }
}
