package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
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
    Spinner levelSpinner;

    ArrayList<Workout> allWorkouts; // 👉 giữ danh sách gốc để lọc lại

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

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
        // Dữ liệu gốc
        allWorkouts = getData();

        // RecyclerView setup
        binding.view1.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.view1.setAdapter(new WorkutAdapter(allWorkouts));

        // Spinner setup
        levelSpinner = findViewById(R.id.levelSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.level_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);

        // Spinner chọn -> lọc danh sách
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLevel = parent.getItemAtPosition(position).toString();
                ArrayList<Workout> filteredList = new ArrayList<>();

                for (Workout workout : allWorkouts) {
                    if (selectedLevel.equals("All") || workout.getLevel().equalsIgnoreCase(selectedLevel)) {
                        filteredList.add(workout);
                    }
                }

                binding.view1.setAdapter(new WorkutAdapter(filteredList));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private ArrayList<Workout> getData() {
        ArrayList<Workout> list = new ArrayList<>();

        list.add(new Workout("Running",
                "You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body",
                "pic_1", 160, "9 min", getLession_1(), "Beginner"));

        list.add(new Workout("Stretching",
                "You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body",
                "pic_2", 230, "85 min", getLession_2(), "Intermediate"));

        list.add(new Workout("Yoga",
                "You just woke up. It is a brand new day. The canvas is blank. How do you begin? Take 21 minutes to cultivate a peaceful mind and strong body",
                "pic_3", 180, "65 min", getLession_3(), "Advanced"));

        list.add(new Workout(
                "Plank",
                "Build core strength with beginner-friendly plank variations. No equipment needed.",
                "plank_main", // ảnh đại diện bài Plank
                100,
                "10 min",
                getLession_Plank()
        ));

        list.add(new Workout(
                "Abs_Workout",
                "Burn belly fat, helps tone core muscles.",
                "abs_main", // ảnh đại diện bài
                120,
                "10 min",
                getLession_Abs_Workout()
        ));

        list.add(new Workout(
                "Leg_Workout",
                "Strengthen your legs, thighs, and buttocks with squats, lunges.",
                "leg_main", // ảnh đại diện bài
                180,
                "20 min",
                getLession_Leg_Workout()
        ));

        list.add(new Workout(
                "Pilates",
                "The exercise combines strength, balance and gentle breath control.",
                "pilates_main", // ảnh đại diện bài
                180,
                "40 min",
                getLession_Pilates()
        ));

        return list;
    }

    private ArrayList<Lession> getLession_1() {
        ArrayList<Lession> list = new ArrayList<>();


        list.add(new Lession("Lesson 1","2UT67RyDces?si=_VedtaWmwkE03hHB","HBPMvFkpNgE","pic_1_1"));

        list.add(new Lession("Lesson 2","MiLuh0RpEGk?si=L1ROX2XGrcT0PLRu","K6124WgiiPw","pic_1_2"));

        list.add(new Lession("Lesson 3","01:57","Zc08v4YY0eA","pic_1_3"));


        return list;
    }

    private ArrayList<Lession> getLession_2() {
        ArrayList<Lession> list = new ArrayList<>();
        list.add(new Lession("Lesson 1", "20:23", "L3eImBAXT7I", "pic_3_1"));
        list.add(new Lession("Lesson 2", "18:27", "47Exgz07FLU", "pic_3_2"));
        list.add(new Lession("Lesson 3", "32:25", "0mLx8tmaQ-4", "pic_3_3"));
        list.add(new Lession("Lesson 4", "07:52", "w86EalEoFRY", "pic_3_4"));
        return list;
    }

    private ArrayList<Lession> getLession_3() {
        ArrayList<Lession> list = new ArrayList<>();
        list.add(new Lession("Lesson 1", "23:00", "v7AYKMP6r0E", "pic_3_1"));
        list.add(new Lession("Lesson 2", "27:00", "Eml2xnoLpYE", "pic_3_2"));
        list.add(new Lession("Lesson 3", "25:00", "v7SN-d4qXx0", "pic_3_3"));
        list.add(new Lession("Lesson 4", "21:00", "LqXZ628YNj4", "pic_3_4"));
        return list;
    }


    private ArrayList<Lession> getLession_Plank() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Elbow Plank", "qmw7-IFVZPo?si=WaKDbw34j4km1Ji6", "VaoqlbNAdBo", "plank1"));
        list.add(new Lession("Side Plank", "NXr4Fw8q60o?si=KWiE5fq8mX5Lo8xt", "k2VlkRJ0N3g", "plank2"));
        list.add(new Lession("Plank with Leg Lift", "rGpw_cUfIAg?si=aAwmZOwMCYwXikqu", "dFHeYxk7tpg", "plank3"));

        return list;
    }

    private ArrayList<Lession> getLession_Abs_Workout() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson Lower", "Kd8ZU9NUbRA?si=yfxmeO8r2HGJKwqC", "VaoqlbNAdBo", "abs1.jpg"));
        list.add(new Lession("Lesson Upper", "0_nKiv0i7PA?si=cRFZCqEKog8omfcQ", "k2VlkRJ0N3g", "abs2.jpg"));
        list.add(new Lession("Lesson Oblique", "Szel63xuAxc?si=4nXqHsZaSEld7lue", "dFHeYxk7tpg", "abs3.jpg"));

        return list;
    }

    private ArrayList<Lession> getLession_Leg_Workout() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson Squat", "r78WzW27-UI?si=6T4yDAWy2RU7Kugo", "00:20", "leg1"));
        list.add(new Lession("Lesson Static Lunge R", "r78WzW27-UI?si=YyNUb4oW3ZNhp55m", "06:20", "leg2"));
        list.add(new Lession("Lesson Deadlift", "r78WzW27-UI?si=6T4yDAWy2RU7Kugo", "04:20", "leg3"));

        return list;
    }

    private ArrayList<Lession> getLession_Pilates() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson 1", "zbK-HgqWClA?si=1SOJhoQI1547vjBt", "00:20", "pilates1"));
        list.add(new Lession("Lesson 2", "zbK-HgqWClA?si=1SOJhoQI1547vjBt", "06:20", "pilates2"));
        list.add(new Lession("Lesson 3", "zbK-HgqWClA?si=1SOJhoQI1547vjBt", "04:20", "pilates3"));

        return list;
    }
}