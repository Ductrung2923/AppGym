package com.example.gym.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gym.Adapter.PlanAdapter;
import com.example.gym.R;

import java.util.ArrayList;
import java.util.List;
public class PlanActivity extends AppCompatActivity {

    TextView txtTitle, txtSubtitle;
    ImageView imgCover;
    RecyclerView recyclerView;
    List<String> planList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        txtTitle = findViewById(R.id.txt_title);
        txtSubtitle = findViewById(R.id.txt_subtitle);
        imgCover = findViewById(R.id.img_cover);
        recyclerView = findViewById(R.id.recycler_plans);

        // Nhận dữ liệu
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String subtitle = intent.getStringExtra("subtitle");
        int imageRes = intent.getIntExtra("image", R.drawable.program1);

        txtTitle.setText(title);
        txtSubtitle.setText(subtitle);
        imgCover.setImageResource(imageRes);

        // Danh sách giả định
        planList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            planList.add("PLAN " + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlanAdapter adapter = new PlanAdapter(planList);
        adapter.setContext(this); // 👈 Gửi context vào adapter
        recyclerView.setAdapter(adapter);

    }
}

