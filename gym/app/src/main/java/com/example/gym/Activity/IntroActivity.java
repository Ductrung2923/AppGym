package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.gym.databinding.ActivityIntroBinding;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
       binding.StarBtn.setOnClickListener(v -> startActivity(new Intent(IntroActivity.this, MainActivity.class)));


    }
}