package com.example.gym.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.gym.Adapter.LessionsAdapter;
import com.example.gym.Domain.Workout;
import com.example.gym.R;
import com.example.gym.databinding.ActivityWorkoutBinding;

public class WorkoutActivity extends AppCompatActivity {
    ActivityWorkoutBinding binding;
    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getObject();
        setVariable();

    }

    private void getObject() {
        workout = (Workout) getIntent().getSerializableExtra("object");
    }

    private void setVariable() {
        int resId = getResources().getIdentifier(workout.getPicPath(), "drawable", getPackageName());

        Glide.with(this)
                .load(resId)
                .into(binding.pic);


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.TitleTxt.setText(workout.getTitle());
        binding.excersizeTxt.setText(workout.getLessions().size()+" Exercise");
        binding.kcalTxt.setText(workout.getKcal()+" Kcal");
        binding.durationeTxt.setText(workout.getDurationAll());
        binding.descriptionTxt.setText(workout.getDescription());
        binding.View3.setLayoutManager(new LinearLayoutManager( WorkoutActivity.this, LinearLayoutManager. VERTICAL,false));
        binding.View3.setAdapter(new LessionsAdapter(workout.getLessions()));

    }

}