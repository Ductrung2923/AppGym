package com.example.gym.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.R;

public class BMIActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate;
    TextView tvResult, tvBMICategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        // Khởi tạo các View
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);
        tvBMICategory = findViewById(R.id.tvBMICategory);

        // Bắt sự kiện nút tính toán
        btnCalculate.setOnClickListener(v -> {
            try {
                float weight = Float.parseFloat(etWeight.getText().toString());
                float heightCm = Float.parseFloat(etHeight.getText().toString());
                float heightM = heightCm / 100;
                float bmi = weight / (heightM * heightM);

                String result = String.format("Chỉ số BMI của bạn: %.1f", bmi);
                tvResult.setText(result);

                // Phân loại BMI
                String category;
                if (bmi < 16.0) {
                    category = "Thiếu cân nghiêm trọng";
                } else if (bmi < 18.5) {
                    category = "Thiếu cân";
                } else if (bmi < 25.0) {
                    category = "Bình thường";
                } else if (bmi < 30.0) {
                    category = "Thừa cân";
                } else if (bmi < 35.0) {
                    category = "Béo phì cấp độ 1";
                } else if (bmi < 40.0) {
                    category = "Béo phì cấp độ 2";
                } else {
                    category = "Béo phì cấp độ 3";
                }

                tvBMICategory.setText("Phân loại: " + category);

            } catch (Exception e) {
                tvResult.setText("Dữ liệu không hợp lệ!");
                tvBMICategory.setText("");
            }
        });
    }
}