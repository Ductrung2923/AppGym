package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gym.Adapter.WorkutAdapter;
import com.example.gym.Domain.Lession;
import com.example.gym.Domain.Workout;
import com.example.gym.R;
import com.example.gym.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ImageView imgButtMapRun;
    private ImageView buttonProfile;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding.view1.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        binding.view1.setAdapter(new WorkutAdapter(getData()));

        BindingView();
        BindingAction();

    }



    private void BindingView() {
        imgButtMapRun = findViewById(R.id.MapsRun);
        buttonProfile = findViewById(R.id.imgButProfile);

    }

    private void BindingAction() {
        imgButtMapRun.setOnClickListener(this:: MapRunButton);
        buttonProfile.setOnClickListener(this:: ProfileButton);
    }

    private void ProfileButton(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void MapRunButton(View view) {

        startActivity(new Intent(MainActivity.this, TrackingActivity.class));
    }

    private ArrayList<Workout> getData() {

        ArrayList<Workout> list = new ArrayList<>();

        list.add(new Workout ("Running","You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body","pic_1",160,"9 min", getLession_1()));

        list.add(new Workout ("Stretching","You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body","pic_2",230,"85 min", getLession_2()));

        list.add(new Workout ("Yoga","You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body","pic_3",180,"65 min", getLession_3()));

        return list;

    }

    private ArrayList<Lession> getLession_1() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson 1","03:46","HBPMvFkpNgE","pic_1_1"));

        list.add(new Lession("Lesson 2","MiLuh0RpEGk?si=L1ROX2XGrcT0PLRu","K6124WgiiPw","pic_1_2"));

        list.add(new Lession("Lesson 3","01:57","Zc08v4YY0eA","pic_1_3"));

        return list;

    }

    private ArrayList<Lession> getLession_2() {

        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson 1","20:23","L3eImBAXT7I","pic_3_1"));

        list.add(new Lession("Lesson 2","18:27","47Exgz07FLU","pic_3_2"));

        list.add(new Lession("Lesson 3","32:25","0mLx8tmaQ-4","pic_3_3"));

        list.add(new Lession ("Lesson 4","07:52","w86EalEoFRY","pic_3_4"));

        return list;

    }

    private ArrayList<Lession> getLession_3() {

        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson 1", "23:00","v7AYKMP6r0E","pic_3_1"));

        list.add(new Lession("Lesson 2", "27:00", "Eml2xnoLpYE","pic_3_2"));

        list.add(new Lession("Lesson 3", "25:00 ","v7SN-d4qXx0","pic_3_3"));

        list.add(new Lession("Lesson 4", "21:00","LqXZ628YNj4", "pic_3_4"));

        return list;
    }

}