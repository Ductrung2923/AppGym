package com.example.gym.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.gym.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProgressFragment extends Fragment {

    ProgressBar progressBar;
    TextView progressText;
    LinearLayout dateLayout, sessionLayout;
    ImageView btnPrevWeek, btnNextWeek;
    Calendar currentWeekStart;
    TextView weekTitle;
    TextView motivationText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        weekTitle = view.findViewById(R.id.weekTitle);
        motivationText = view.findViewById(R.id.motivationText);

        progressBar = view.findViewById(R.id.progressBar);
        progressText = view.findViewById(R.id.progressText);
        dateLayout = view.findViewById(R.id.dateLayout);
        sessionLayout = view.findViewById(R.id.sessionLayout);
        btnPrevWeek = view.findViewById(R.id.btnPrevWeek);
        btnNextWeek = view.findViewById(R.id.btnNextWeek);

        currentWeekStart = Calendar.getInstance();
        currentWeekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        btnPrevWeek.setOnClickListener(v -> {
            currentWeekStart.add(Calendar.WEEK_OF_YEAR, -1);
            updateWeek();
        });

        btnNextWeek.setOnClickListener(v -> {
            currentWeekStart.add(Calendar.WEEK_OF_YEAR, 1);
            updateWeek();
        });

        updateWeek();

        return view;
    }

    private void updateWeek() {
        dateLayout.removeAllViews();

        Calendar today = Calendar.getInstance();
        Calendar calendar = (Calendar) currentWeekStart.clone();
        SimpleDateFormat sdf = new SimpleDateFormat("dd EEE", Locale.getDefault());

        SimpleDateFormat headerFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        Calendar endOfWeek = (Calendar) calendar.clone();
        endOfWeek.add(Calendar.DAY_OF_MONTH, 6);

        String weekRange = headerFormat.format(calendar.getTime()) + " - " + headerFormat.format(endOfWeek.getTime());
        weekTitle.setText(weekRange);

        for (int i = 0; i < 7; i++) {
            String dateLabel = sdf.format(calendar.getTime());

            TextView dateView = new TextView(requireContext());
            dateView.setText(dateLabel);
            dateView.setTextSize(14);
            dateView.setTextColor(Color.WHITE);
            dateView.setPadding(30, 15, 30, 15);

            if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                    && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                dateView.setBackgroundResource(R.drawable.date_today_bg);
            } else {
                dateView.setBackgroundResource(R.drawable.date_bg);
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(12, 0, 12, 0);
            dateView.setLayoutParams(lp);

            final Calendar selectedDate = (Calendar) calendar.clone();
            dateView.setOnClickListener(v -> {
                for (int j = 0; j < dateLayout.getChildCount(); j++) {
                    View child = dateLayout.getChildAt(j);
                    child.setBackgroundResource(R.drawable.date_bg);
                }

                v.setBackgroundResource(R.drawable.date_today_bg);
                filterSessionsByDate(selectedDate);
                updateProgressForDate(selectedDate);
            });

            dateLayout.addView(dateView);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Calendar now = Calendar.getInstance();
        if (currentWeekStart.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR)
                && currentWeekStart.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            filterSessionsByDate(now);
            updateProgressForDate(now);
        } else {
            filterSessionsByDate(currentWeekStart);
            updateProgressForDate(currentWeekStart);
        }
    }

    private void filterSessionsByDate(Calendar selectedDate) {
        sessionLayout.removeAllViews();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = sdf.format(selectedDate.getTime());
        Log.d("SelectedDate", "Ngày được chọn: " + dateStr);

        if (dateStr.equals("2025-07-13")) {
            addSessionCard("Treadmill", "50 min", R.drawable.treadmill_image);
            addSessionCard("Jogging", "75 min", R.drawable.jogging_image);
        } else if (dateStr.equals("2025-07-14")) {
            addSessionCard("Yoga", "30 min", R.drawable.jogging_image);
        } else {
            TextView noData = new TextView(requireContext());

            noData.setTextSize(16);
            noData.setTextColor(Color.GRAY);
            noData.setGravity(Gravity.CENTER);
            sessionLayout.addView(noData);
        }
    }

    private void updateProgressForDate(Calendar selectedDate) {
        SharedPreferences prefs = requireContext().getSharedPreferences("workout_progress", Context.MODE_PRIVATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = sdf.format(selectedDate.getTime());

        float percent = prefs.getFloat("progress_" + dateStr, -1f);
        int finalPercent;

        if (percent >= 0) {
            int displayPercent = Math.min(100, Math.round(percent));
            progressBar.setProgress(displayPercent);
            progressText.setText(displayPercent + "%");
            finalPercent = displayPercent;
        } else {
            int completedMinutes = prefs.getInt("completed_" + dateStr, 0);
            int goalMinutes = 15;
            int fallbackPercent = (int) ((completedMinutes / (float) goalMinutes) * 100);
            fallbackPercent = Math.max(0, Math.min(100, fallbackPercent));
            progressBar.setProgress(fallbackPercent);
            progressText.setText(fallbackPercent + "%");
            finalPercent = fallbackPercent;
        }

        // 🎯 Thêm lời chúc theo phần trăm
        if (finalPercent < 30) {
            motivationText.setText("Keep going, you’ve got this!");
        } else if (finalPercent < 99) {
            motivationText.setText("Stay consistent, you’re doing great!");
        } else {
            motivationText.setText("Congratulations! You've completed your goal!");
        }

    }


    // ✅ ĐÃ di chuyển vào đúng trong class
    private void addSessionCard(String title, String time, int imageRes) {
        CardView card = new CardView(getContext());
        card.setCardElevation(8);
        card.setRadius(16);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        ImageView image = new ImageView(getContext());
        image.setImageResource(imageRes);
        image.setLayoutParams(new ViewGroup.LayoutParams(300, 200));

        TextView text = new TextView(getContext());
        text.setText(title + "\n" + time);
        text.setGravity(Gravity.CENTER);
        text.setPadding(0, 10, 0, 0);

        layout.addView(image);
        layout.addView(text);
        card.addView(layout);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f);
        lp.setMargins(8, 0, 8, 0);
        card.setLayoutParams(lp);

        sessionLayout.addView(card);
    }
}
