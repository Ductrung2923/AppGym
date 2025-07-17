package com.example.gym.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Adapter.ExerciseAdapter;
import com.example.gym.Model.Exercise;
import com.example.gym.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanDetailActivity extends AppCompatActivity {

    TextView txtPlanTitle, txtInfo;
    ImageView imgBack;
    RecyclerView recyclerView;

    List<Exercise> exerciseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);

        txtPlanTitle = findViewById(R.id.txt_plan_title);
        txtInfo = findViewById(R.id.txt_info);
        imgBack = findViewById(R.id.img_back);
        recyclerView = findViewById(R.id.recycler_exercises);

        // Nhận tên PLAN
        String planName = getIntent().getStringExtra("planName");
        txtPlanTitle.setText(planName);
        txtInfo.setText("Full Body | 6 Exercises | 54 Min | 252 Cal");

        // Tạo danh sách bài tập (giả lập)
        exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("Dips", "4 sets", R.drawable.dips, R.drawable.ic_chest));
        exerciseList.add(new Exercise("Goblet Squat", "3 sets x 8-10 reps", R.drawable.goblet_squat, R.drawable.ic_leg));
        exerciseList.add(new Exercise("Overhead Press", "3 sets x 10-12 reps", R.drawable.overhead_press, R.drawable.ic_shoulder));
        exerciseList.add(new Exercise("Lat Pulldown", "4 sets x 10-12 reps", R.drawable.lat_pulldown, R.drawable.ic_back));
        exerciseList.add(new Exercise("Back Extension", "3 sets x 8-10 reps", R.drawable.back_extension, R.drawable.ic_back));
        exerciseList.add(new Exercise("Leg Curl", "3 sets x 10-12 reps", R.drawable.leg_curl, R.drawable.ic_leg));

        exerciseList.add(new Exercise("Selector Chest Press Machine", "4 sets x 10-12 reps", R.drawable.program1, R.drawable.ic_chest));
        exerciseList.add(new Exercise("Hack Squat", "3 sets x 8-10 reps", R.drawable.program1, R.drawable.ic_leg));
        exerciseList.add(new Exercise("Superman", "3 sets", R.drawable.program1, R.drawable.ic_back));
        exerciseList.add(new Exercise("Decline Russian Twist", "4 sets x 8-10 reps", R.drawable.program1, R.drawable.program1));
        exerciseList.add(new Exercise("Chest Supported Dumbbell Row", "4 sets x 8-10 reps", R.drawable.program1, R.drawable.ic_back));
        exerciseList.add(new Exercise("Ab Machine", "4 sets x 8-10 reps", R.drawable.program1, R.drawable.program1));
        exerciseList.add(new Exercise("Underhand Grip Pull Up", "3 sets", R.drawable.program1, R.drawable.ic_back));
        exerciseList.add(new Exercise("Decline Barbell Bench Press, Close Grip", "4 sets x 6-8 reps", R.drawable.program1, R.drawable.ic_chest));
        exerciseList.add(new Exercise("Inverted Row, Underhand Grip", "3 sets", R.drawable.program1, R.drawable.ic_back));
        exerciseList.add(new Exercise("Standing Cable Hammer Curl, Single Arm", "2 sets x 8-10 reps", R.drawable.program1, R.drawable.program1));
        exerciseList.add(new Exercise("Barbell Bench Press", "4 sets x 6-8 reps", R.drawable.program1, R.drawable.ic_chest));
        exerciseList.add(new Exercise("Selector Chest Supported Row Machine, Overhand Grip", "4 sets x 8-10 reps", R.drawable.program1, R.drawable.ic_back));

        Collections.shuffle(exerciseList); // Đảo ngẫu nhiên
        exerciseList = exerciseList.subList(0, 6); // Lấy 6 bài đầu

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ExerciseAdapter(exerciseList));

        // Xử lý nút back
        imgBack.setOnClickListener(v -> finish());
        Button btnStart = findViewById(R.id.btn_start_plan);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(PlanDetailActivity.this, WorkoutDetailActivity.class);
            startActivity(intent);
        });

    }
}
//
