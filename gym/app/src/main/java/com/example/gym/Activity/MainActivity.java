package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
    private LinearLayout favoriteTab;
    private ImageView cartIcon;
    private CoordinatorLayout bottomNavBar;
    private ImageView calculateIcon;
    ActivityMainBinding binding;
    Spinner levelSpinner;

    ArrayList<Workout> allWorkouts; // Toàn bộ danh sách workout gốc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        calculateIcon = findViewById(R.id.calculate);

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
        calculateIcon.setOnClickListener(v -> {
            bottomNavBar.setVisibility(View.GONE); // Ẩn thanh điều hướng dưới

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.anim.slide_in_left,
                            android.R.anim.fade_out,
                            android.R.anim.slide_in_left,
                            android.R.anim.fade_out
                    )
                    .replace(R.id.main, new LibraryFragment()) // Thay frame chính bằng LibraryFragment
                    .addToBackStack("library")
                    .commit();
        });

    }

    // ✅ Thiết lập Spinner và lọc danh sách
    private void setupSpinner() {
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

        list.add(new Workout(
                "Plank",
                "Build core strength with beginner-friendly plank variations. No equipment needed.",
                "plank_main",
                100,
                "10 min",
                getLession_Plank(),
                "Beginner"
        ));


        list.add(new Workout(
                "Abs_Workout",
                "Burn belly fat, helps tone core muscles.",
                "abs_main",
                120,
                "10 min",
                getLession_Abs_Workout(),
                "Intermediate"
        ));

        list.add(new Workout(
                "Leg_Workout",
                "Strengthen your legs, thighs, and buttocks with squats, lunges.",
                "leg_main",
                180,
                "20 min",
                getLession_Leg_Workout(),
                "Advanced"
        ));


        list.add(new Workout(
                "Pilates",
                "The exercise combines strength, balance and gentle breath control.",
                "pilates_main",
                180,
                "40 min",
                getLession_Pilates(),
                "Beginner"
        ));


        return list;
    }

    private ArrayList<Lession> getLession_1() {
        ArrayList<Lession> list = new ArrayList<>();


        list.add(new Lession("Lesson 1","vBVoodacTf8?si=aj7eVDJtrHE9Xha8","23:11","pic_1_1"));


        list.add(new Lession("Lesson 2","TjVp-uoTviA?si=3W1XV5NBrHKsTZVD","13:27","pic_1_2"));


        list.add(new Lession("Lesson 3","AiohKZonPbo?si=luZ2DS4JDpfOmXGd","8:15","pic_1_3"));


        return list;
    }

    private ArrayList<Lession> getLession_2() {
        ArrayList<Lession> list = new ArrayList<>();
        list.add(new Lession("Lesson 1", "itJE4neqDJw?si=LA9Hrjum62fvi0ui", "14:17", "pic_2_1"));
        list.add(new Lession("Lesson 2", "i6TzP2COtow?si=G7s4z4pSHSTj8DqA", "16:02", "pic_2_2"));
        list.add(new Lession("Lesson 3", "FI51zRzgIe4?si=CmM-e3nNtVge5ahI", "8:55", "pic_2_3"));
        list.add(new Lession("Lesson 4", "t2jel6q1GRk?si=RdSMqZzLwKVozc9d", "9:11", "pic_2_4"));
        return list;
    }

    private ArrayList<Lession> getLession_3() {
        ArrayList<Lession> list = new ArrayList<>();
        list.add(new Lession("Lesson 1", "23:00", "v7AYKMP6r0E", "pic_3_1"));
        list.add(new Lession("Lesson 2", "Eml2xnoLpYE?si=TKnA9Om8FTBh-bEQ", "25:36", "pic_3_2"));
        list.add(new Lession("Lesson 3", "v7SN-d4qXx0?si=eAb36da5YGRap2SO", "19:08", "pic_3_3"));
        list.add(new Lession("Lesson 4", "CM43AZaRXNw?si=w3-XDydKpZJRGMEs", "18:33", "pic_3_4"));
        return list;
    }


    private ArrayList<Lession> getLession_Plank() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Elbow Plank", "qmw7-IFVZPo?si=WaKDbw34j4km1Ji6", "0:57", "plank1"));
        list.add(new Lession("Side Plank", "NXr4Fw8q60o?si=KWiE5fq8mX5Lo8xt", "1:54", "plank2"));
        list.add(new Lession("Plank with Leg Lift", "rGpw_cUfIAg?si=aAwmZOwMCYwXikqu", "0:25", "plank3"));

        return list;
    }

    private ArrayList<Lession> getLession_Abs_Workout() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson Lower", "Kd8ZU9NUbRA?si=yfxmeO8r2HGJKwqC", "5:26", "abs1"));
        list.add(new Lession("Lesson Upper", "0_nKiv0i7PA?si=cRFZCqEKog8omfcQ", "5:27", "abs2"));
        list.add(new Lession("Lesson Oblique", "Szel63xuAxc?si=4nXqHsZaSEld7lue", "8:30", "abs3"));

        return list;
    }

    private ArrayList<Lession> getLession_Leg_Workout() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson Squat", "r78WzW27-UI?si=6T4yDAWy2RU7Kugo", "20:06", "leg1"));
        list.add(new Lession("Lesson Static Lunge R", "xoZMv_YJtkA?si=M9C9dm6bLc2E0CKr", "0:28", "leg2"));
        list.add(new Lession("Lesson Deadlift", "ntr64W6ZWB0?si=qP0M9YzmUzc20Nst", "14:16", "leg3"));

        return list;
    }

    private ArrayList<Lession> getLession_Pilates() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lesson 1", "zbK-HgqWClA?si=1SOJhoQI1547vjBt", "15:03", "pilates1"));
        list.add(new Lession("Lesson 2", "hpyT2v04Bj0?si=eJbDIPf29FEzbwsU", "09:19", "pilates2"));
        list.add(new Lession("Lesson 3", "C2HX2pNbUCM?si=8N_7dC5jt99IF2zm", "32:36", "pilates3"));

        return list;
    }
    @Override
    public void onBackPressed() {
        // Kiểm tra xem còn Fragment nào trong back stack không
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước

            // Hiện lại thanh bottom nav
            CoordinatorLayout bottomNavBar = findViewById(R.id.bottomNavBar);
            bottomNavBar.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed(); // Thoát Activity nếu không còn fragment nào
        }
    }

}