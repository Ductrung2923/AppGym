package com.example.gym.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WorkoutDetailActivity extends AppCompatActivity {

    private TextView timerText;
    private Button btnStart, btnPauseResume;

    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private boolean isStarted = false;
    private Button btnIncreaseTime, btnDecreaseTime;

    private long remainingMillis = 30 * 60 * 1000; // 1 phút mặc định
    private long totalMillis = remainingMillis;    // Tổng thời gian để tính phần trăm

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        timerText = findViewById(R.id.timerText);
        btnStart = findViewById(R.id.btnStart);
        btnPauseResume = findViewById(R.id.btnPauseResume);

        updateTimerText(); // Hiển thị thời gian ban đầu

        btnPauseResume.setEnabled(false);

        btnStart.setOnClickListener(v -> {
            if (!isStarted) {
                startTimer();
                isStarted = true;
                btnStart.setEnabled(false);
                btnIncreaseTime.setEnabled(false);
                btnDecreaseTime.setEnabled(false);
                btnPauseResume.setEnabled(true);
            }
        });

        btnPauseResume.setOnClickListener(v -> {
            if (isRunning) {
                pauseTimer();
            } else {
                resumeTimer();
            }
        });
        btnIncreaseTime = findViewById(R.id.btnIncreaseTime);
        btnDecreaseTime = findViewById(R.id.btnDecreaseTime);
        btnIncreaseTime.setOnClickListener(v -> {
            if (!isStarted) {
                remainingMillis += 60 * 1000; // +1 phút
                totalMillis = remainingMillis;
                updateTimerText();
            }
        });

        btnDecreaseTime.setOnClickListener(v -> {
            if (!isStarted && remainingMillis > 60 * 1000) {
                remainingMillis -= 60 * 1000; // -1 phút
                totalMillis = remainingMillis;
                updateTimerText();
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(remainingMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                remainingMillis = 0;
                updateTimerText();
                btnPauseResume.setEnabled(false);

                saveProgressPercent(); // 100% hoàn thành
            }
        };
        countDownTimer.start();
        isRunning = true;
        btnPauseResume.setText("Pause");
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        btnPauseResume.setText("Resume");
    }

    private void resumeTimer() {
        startTimer();
    }

    private void updateTimerText() {
        int minutes = (int) (remainingMillis / 1000) / 60;
        int seconds = (int) (remainingMillis / 1000) % 60;
        String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerText.setText(time);
    }

    // ✅ Lưu tiến độ phần trăm chính xác
    private void saveProgressPercent() {
        float percent = 100f * (1f - remainingMillis / (float) totalMillis);
        percent = Math.max(0f, Math.min(100f, percent)); // đảm bảo nằm trong [0, 100]

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        SharedPreferences prefs = getSharedPreferences("workout_progress", MODE_PRIVATE);
        prefs.edit().putFloat("progress_" + today, percent).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isStarted) {
            saveProgressPercent(); // Lưu phần trăm trước khi rời Activity
        }
    }
}
