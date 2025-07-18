package com.example.gym.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.gym.R;

import java.util.Calendar;

public class WorkoutReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "workout_reminder_channel";
    private static final int NOTIFICATION_ID = 1001;
    private static final String TAG = "WorkoutReminder";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm received!");

        String message = intent.getStringExtra("message");
        if (message == null) {
            message = "Hãy tập luyện cùng Workout App";
        }

        createNotificationChannel(context);
        showNotification(context, message);

        // Đặt lại alarm cho tuần tiếp theo
        rescheduleAlarm(context, intent);
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Workout Reminders";
            String description = "Nhắc nhở thời gian tập luyện";
            int importance = NotificationManager.IMPORTANCE_HIGH; // Đã sửa thành HIGH

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{0, 250, 250, 250});

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showNotification(Context context, String message) {
        // Tạo intent để mở app khi click notification
        Intent appIntent = new Intent(context, MainActivity.class);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Workout App")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setVibrate(new long[]{0, 250, 250, 250})
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // QUAN TRỌNG: Thực sự hiển thị notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        Log.d(TAG, "Notification shown: " + message);
    }

    @SuppressLint("ScheduleExactAlarm")
    private void rescheduleAlarm(Context context, Intent originalIntent) {
        // Lấy thông tin từ intent để đặt lại alarm cho tuần tiếp theo
        int dayOfWeek = originalIntent.getIntExtra("dayOfWeek", -1);
        int hour = originalIntent.getIntExtra("hour", 8);
        int minute = originalIntent.getIntExtra("minute", 0);

        if (dayOfWeek != -1) {
            // Đặt lại alarm cho tuần tiếp theo
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, 1); // Tuần tiếp theo
            calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            Intent newIntent = new Intent(context, WorkoutReminderReceiver.class);
            newIntent.putExtra("message", "Hãy tập luyện cùng Workout App");
            newIntent.putExtra("dayOfWeek", dayOfWeek);
            newIntent.putExtra("hour", hour);
            newIntent.putExtra("minute", minute);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context,
                    dayOfWeek,
                    newIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

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

            Log.d(TAG, "Alarm rescheduled for next week");
        }
    }
}