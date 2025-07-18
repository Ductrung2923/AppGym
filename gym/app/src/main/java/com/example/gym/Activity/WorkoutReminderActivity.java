package com.example.gym.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gym.R;

import java.util.Calendar;
import java.util.Locale;

public class WorkoutReminderActivity extends AppCompatActivity {

    private TextView tvSelectedTime;
    private CheckBox cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday;
    private Button btnSave, btnCancel;

    private int selectedHour = 8;
    private int selectedMinute = 0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_workout);

// Kiểm tra quyền notification (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 100);
            }
        }        initViews();
        setupListeners();
        loadSavedSettings();
    }

    private void initViews() {
        tvSelectedTime = findViewById(R.id.tvSelectedTime);
        cbMonday = findViewById(R.id.cbMonday);
        cbTuesday = findViewById(R.id.cbTuesday);
        cbWednesday = findViewById(R.id.cbWednesday);
        cbThursday = findViewById(R.id.cbThursday);
        cbFriday = findViewById(R.id.cbFriday);
        cbSaturday = findViewById(R.id.cbSaturday);
        cbSunday = findViewById(R.id.cbSunday);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        sharedPreferences = getSharedPreferences("WorkoutReminder", Context.MODE_PRIVATE);
    }

    private void setupListeners() {
        tvSelectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReminderSettings();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;
                        updateTimeDisplay();
                    }
                },
                selectedHour,
                selectedMinute,
                true
        );
        timePickerDialog.show();
    }

    private void updateTimeDisplay() {
        String timeString = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
        tvSelectedTime.setText(timeString);
    }

    private void saveReminderSettings() {
        // Kiểm tra xem có ít nhất một ngày được chọn
        if (!isAnyDaySelected()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một ngày trong tuần", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu cài đặt
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("reminder_hour", selectedHour);
        editor.putInt("reminder_minute", selectedMinute);
        editor.putBoolean("monday", cbMonday.isChecked());
        editor.putBoolean("tuesday", cbTuesday.isChecked());
        editor.putBoolean("wednesday", cbWednesday.isChecked());
        editor.putBoolean("thursday", cbThursday.isChecked());
        editor.putBoolean("friday", cbFriday.isChecked());
        editor.putBoolean("saturday", cbSaturday.isChecked());
        editor.putBoolean("sunday", cbSunday.isChecked());
        editor.putBoolean("reminder_enabled", true);
        editor.apply();

        // Đặt alarm cho các ngày đã chọn
        setWeeklyAlarms();

        Toast.makeText(this, "Nhắc nhở đã được lưu thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isAnyDaySelected() {
        return cbMonday.isChecked() || cbTuesday.isChecked() || cbWednesday.isChecked() ||
                cbThursday.isChecked() || cbFriday.isChecked() || cbSaturday.isChecked() || cbSunday.isChecked();
    }

    @SuppressLint("ScheduleExactAlarm")
    private void setWeeklyAlarms() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        CheckBox[] checkBoxes = {cbSunday, cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday};

        for (int i = 0; i < checkBoxes.length; i++) {
            cancelAlarm(i);

            if (checkBoxes[i].isChecked()) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, i + 1);
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                calendar.set(Calendar.SECOND, 0);

                if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                    calendar.add(Calendar.WEEK_OF_YEAR, 1);
                }

                Intent intent = new Intent(this, WorkoutReminderReceiver.class);
                intent.putExtra("message", "Hãy tập luyện cùng Workout App");
                intent.putExtra("dayOfWeek", i + 1); // Thêm thông tin này
                intent.putExtra("hour", selectedHour);
                intent.putExtra("minute", selectedMinute);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        this, i, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );

                // Sử dụng setExactAndAllowWhileIdle thay vì setRepeating
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                } else {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                }

                Log.d("WorkoutReminder", "Alarm set for day " + (i+1) + " at " + selectedHour + ":" + selectedMinute);
            }
        }


    }

    private void cancelAlarm(int dayOfWeek) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, WorkoutReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                dayOfWeek,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        alarmManager.cancel(pendingIntent);
    }

    private void loadSavedSettings() {
        selectedHour = sharedPreferences.getInt("reminder_hour", 8);
        selectedMinute = sharedPreferences.getInt("reminder_minute", 0);

        cbMonday.setChecked(sharedPreferences.getBoolean("monday", false));
        cbTuesday.setChecked(sharedPreferences.getBoolean("tuesday", false));
        cbWednesday.setChecked(sharedPreferences.getBoolean("wednesday", false));
        cbThursday.setChecked(sharedPreferences.getBoolean("thursday", false));
        cbFriday.setChecked(sharedPreferences.getBoolean("friday", false));
        cbSaturday.setChecked(sharedPreferences.getBoolean("saturday", false));
        cbSunday.setChecked(sharedPreferences.getBoolean("sunday", false));

        updateTimeDisplay();
    }

    public void cancelAllReminders() {
        for (int i = 0; i < 7; i++) {
            cancelAlarm(i);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("reminder_enabled", false);
        editor.apply();

        Toast.makeText(this, "Tất cả nhắc nhở đã được hủy", Toast.LENGTH_SHORT).show();
    }
}
