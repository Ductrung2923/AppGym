package com.example.gym.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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

<<<<<<< Updated upstream
=======
    private ImageView imgButtMapRun;
    private ImageView buttonProfile;
    private LinearLayout favoriteTab;
    private ImageView cartIcon;
    private CoordinatorLayout bottomNavBar;

>>>>>>> Stashed changes
    ActivityMainBinding binding;
    Spinner levelSpinner;

    ArrayList<Workout> allWorkouts; // Toàn bộ danh sách workout gốc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
<<<<<<< Updated upstream
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // Dữ liệu gốc
        allWorkouts = getData();

        // RecyclerView setup
        binding.view1.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.view1.setAdapter(new WorkutAdapter(allWorkouts));

        // Spinner setup
        levelSpinner = findViewById(R.id.levelSpinner);
=======

        allWorkouts = getData(); // 👉 Dữ liệu gốc

        // Setup RecyclerView
        binding.view1.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.view1.setAdapter(new WorkutAdapter(allWorkouts));

        BindingView();
        BindingAction();
        setupSpinner(); // 🟢 Gọi hàm Spinner lọc bài tập
    }

    private void BindingView() {
        imgButtMapRun = findViewById(R.id.MapsRun);
        buttonProfile = findViewById(R.id.imgButProfile);
        favoriteTab = findViewById(R.id.nav_favorite);
        cartIcon = findViewById(R.id.cartIcon);
        bottomNavBar = findViewById(R.id.bottomNavBar);
        levelSpinner = findViewById(R.id.levelSpinner); // 🟢 spinner đã được khai báo ở layout
    }

    private void BindingAction() {
        imgButtMapRun.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TrackingActivity.class));
        });

        buttonProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        favoriteTab.setOnClickListener(view -> {
            bottomNavBar.setVisibility(View.GONE);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.anim.slide_in_left,
                            android.R.anim.fade_out,
                            android.R.anim.slide_in_left,
                            android.R.anim.fade_out
                    )
                    .replace(R.id.main, new ProgressFragment())
                    .addToBackStack("progress")
                    .commit();
        });

        cartIcon.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WorkoutDetailActivity.class);
            startActivity(intent);
        });

        // Mở BMIActivity
        binding.calculate.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BMIActivity.class));
        });
    }

    // ✅ Thiết lập Spinner và lọc danh sách
    private void setupSpinner() {
>>>>>>> Stashed changes
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.level_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);

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

        return list;
    }

    private ArrayList<Lession> getLession_1() {
        ArrayList<Lession> list = new ArrayList<>();
        list.add(new Lession("Lesson 1", "03:46", "HBPMvFkpNgE", "pic_1_1"));
        list.add(new Lession("Lesson 2", "03:41", "K6124WgiiPw", "pic_1_2"));
        list.add(new Lession("Lesson 3", "01:57", "Zc08v4YY0eA", "pic_1_3"));
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
}
