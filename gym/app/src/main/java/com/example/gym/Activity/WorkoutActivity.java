package com.example.gym.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gym.Adapter.LessionsAdapter;
import com.example.gym.Domain.Workout;
import com.example.gym.databinding.ActivityWorkoutBinding;

public class WorkoutActivity extends AppCompatActivity {

    ActivityWorkoutBinding binding;
    private Workout workout;
    private LessionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lấy workout được truyền qua từ Intent
        workout = (Workout) getIntent().getSerializableExtra("object");

        // Đổ dữ liệu vào UI
        setVariable();

        // Gán adapter cho RecyclerView
        adapter = new LessionsAdapter(this, workout.getLessions());
        binding.View3.setLayoutManager(new LinearLayoutManager(this));
        binding.View3.setAdapter(adapter);

        // Xử lý nút RESET WORKOUT
        binding.button3.setText("RESET WORKOUT");
        binding.button3.setOnClickListener(v -> resetWorkoutProgress());
    }

    private void setVariable() {
        int drawableResId = getResources().getIdentifier(workout.getPicPath(), "drawable", getPackageName());
        binding.pic.setImageResource(drawableResId);

        binding.TitleTxt.setText(workout.getTitle());
        binding.descriptionTxt.setText(workout.getDescription());
        binding.kcalTxt.setText(workout.getKcal() + " kcal");
        binding.durationeTxt.setText(workout.getDurationAll());
        binding.excersizeTxt.setText(workout.getLessions().size() + " Exercises");

        // Quay lại màn hình trước
        binding.backBtn.setOnClickListener(v -> finish());
    }

    // RESET trạng thái "đã xem" các bài học
    private void resetWorkoutProgress() {
        SharedPreferences preferences = getSharedPreferences("watched_videos", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (int i = 0; i < workout.getLessions().size(); i++) {
            String key = "watched_" + workout.getLessions().get(i).getLink();
            editor.remove(key); // Xoá từng trạng thái
        }

        editor.apply(); // Lưu lại thay đổi

        // Cập nhật UI: gọi lại notifyDataSetChanged()
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Workout progress has been reset!", Toast.LENGTH_SHORT).show();
    }
}
