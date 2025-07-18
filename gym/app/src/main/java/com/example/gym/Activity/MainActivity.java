package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private LinearLayout favoriteTab;
    private ImageView cartIcon;
    private ImageView notification_app;
    private CoordinatorLayout bottomNavBar;

    private ImageView pic;

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
        setupSpinner();
        if (getIntent().hasExtra("selected_workout")) {
            String selectedWorkout = getIntent().getStringExtra("selected_workout");
            showOverlay(selectedWorkout);
        }

    }

    private void BindingView() {
        imgButtMapRun = findViewById(R.id.MapsRun);
        favoriteTab = findViewById(R.id.nav_favorite);
        cartIcon = findViewById(R.id.cartIcon);
        bottomNavBar = findViewById(R.id.bottomNavBar);
        levelSpinner = findViewById(R.id.levelSpinner);
        notification_app = findViewById(R.id.notification_app);
        pic = findViewById(R.id.pic);
    }

    private void BindingAction() {
        imgButtMapRun.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TrackingActivity.class));
        });

        pic.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TrackingActivity.class));
        });


        notification_app.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, WorkoutReminderActivity.class));
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
                "Advanced"
        ));

        list.add(new Workout(
                "Burpee",
                "Step 1: Stand straight, feet shoulder-width apart, arms naturally relaxed.\n" +
                        "Step 2: Lower yourself into a squat position, placing your hands on the floor in front of your toes.\n" +
                        "Step 3: Jump or step your feet back into a plank position, keeping your body in a straight line from head to heels.\n" +
                        "Step 4 (optional): Do 1 push-up to increase the challenge, very suitable when you want to improve the effectiveness of home gym exercises for men.\n" +
                        "Step 5: Jump or step your feet closer to your hands, return to a squat position.\n" +
                        "Step 6: Jump straight up, arms reaching high above your head to activate your entire body muscles.\n" +
                        "Step 7: Land gently on your toes, then lower your entire foot to the floor, keeping your balance.\n" +
                        "Step 8: Repeat the steps for the required number of times.",
                "burpee_main",
                150,
                "20 min",
                getLession_Burpee(),
                "Beginner"
        ));

        list.add(new Workout(
                "Jumping_Jacks",
                "Step 1: Stand straight, feet together, arms straight out to the sides.\n" +
                        "\n" +
                        "Step 2: Jump with feet wider than shoulder-width apart, while swinging arms high above your head.\n" +
                        "\n" +
                        "Step 3: Jump back to starting position.\n" +
                        "\n" +
                        "Step 4: Repeat continuously.",
                "jumping_main",
                120,
                "20 min",
                getLession_Jumping_Jacks(),
                "Beginner"
        ));

        list.add(new Workout(
                "Mountain_Climber",
                "Step 1: Start in a high plank position (arms straight, torso straight).\n" +
                        "Step 2: Tighten your abs, pull your right knee toward your chest.\n" +
                        "Step 3: Quickly return your right leg to the starting position while pulling your left knee toward your chest.\n" +
                        "Step 4: Alternate legs continuously.",
                "mountain_main",
                130,
                "30 min",
                getLession_Mountain_Climber(),
                "Beginner"
        ));

        list.add(new Workout(
                "Push_up",
                "Step 1: High plank position, hands on the floor, slightly wider than shoulder width, fingers pointing forward.\n" +
                        "\n" +
                        "Step 2: Body forms a straight line from head to heels. Tighten abdominal and gluteal muscles.\n" +
                        "\n" +
                        "Step 3: Slowly lower body by bending elbows (pointing back slightly about 45 degrees), until chest almost touches the floor.\n" +
                        "\n" +
                        "Step 4: Use the force from chest muscles and arms to push body back to the starting position.\n" +
                        "\n" +
                        "3-4 sets of 8-15 reps each, rest 60 seconds.",
                "push_up_main",
                180,
                "40 min",
                getLession_Push_up(),
                "Intermediate"
        ));

        list.add(new Workout(
                "Squat",
                "Step 1: Stand up straight, feet shoulder-width apart or wider, toes pointing slightly outward.\n" +
                        "\n" +
                        "Step 2: Hands out in front of chest or behind head (do not pull head).\n" +
                        "\n" +
                        "Step 3: Push hips back and lower body as if sitting in an invisible chair.\n" +
                        "\n" +
                        "Step 4: Lower until thighs are parallel to floor (or deeper if joints allow, back straight). Knees follow toes, trying not to go beyond toes.\n" +
                        "\n" +
                        "Step 5: Keep back naturally straight, chest open, eyes looking forward. Tighten abs.\n" +
                        "\n" +
                        "Step 6: Push force into heels and mid-foot, push body up straight. Squeeze glutes at top.",
                "squat_main",
                150,
                "20 min",
                getLession_Squat(),
                "Intermediate"
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
        list.add(new Lession("Lesson 1", "Kvoq4luIYVc", "0:01", "pic_3_1"));
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

    private ArrayList<Lession> getLession_Burpee() {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lession", "pgM9UIp_Oyw?si=agrqVTE69FlcYpqB", "6:20", "burpee_1"));
        return list;
    }

    private ArrayList<Lession> getLession_Jumping_Jacks () {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lession", "FmnrehDxEB0?si=gekPjoRTSgB-WB11", "0:13", "jumping_1"));
        return list;
    }

    private ArrayList<Lession> getLession_Mountain_Climber () {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lession", "De3Gl-nC7IQ?si=zvNPMmJRSLCCwhr0", "1:15", "mountain_1"));
        return list;
    }

    private ArrayList<Lession> getLession_Push_up () {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lession", "WDIpL0pjun0?si=wYdyUQYgnBatAfyq", "0:13", "push_up1"));
        return list;
    }

    private ArrayList<Lession> getLession_Squat () {
        ArrayList<Lession> list = new ArrayList<>();

        list.add(new Lession("Lession", "xqvCmoLULNY?si=Ofq3iN95IVwOs7ri", "0:48", "squat1"));
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

    public void showOverlay(String workoutName) {
        View overlayView = getLayoutInflater().inflate(R.layout.workout_overlay, null);
        ConstraintLayout rootLayout = findViewById(R.id.main);

        TextView workoutNameText = overlayView.findViewById(R.id.overlayWorkoutName);
        Button closeButton = overlayView.findViewById(R.id.overlayCloseButton);

        workoutNameText.setText(workoutName);

        closeButton.setOnClickListener(v -> {
            rootLayout.removeView(overlayView);
        });

        rootLayout.addView(overlayView);
    }



}