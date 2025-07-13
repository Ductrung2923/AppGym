package com.example.gym.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gym.Data.DatabaseHelper;
import com.example.gym.Model.UserSelection;
import com.example.gym.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseOption extends AppCompatActivity {

    private TextView titleText;
    private LinearLayout buttonContainer;
    private Button backButton;
    private List<String> navigationHistory;
    private String currentSelection = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);

        initViews();
        setupInitialScreen();
        BindingView();
        BindingAction();
    }

    private void BindingAction() {
    }

    private void BindingView() {

    }

    private void initViews() {
        try {
            titleText = findViewById(R.id.titleText);
            buttonContainer = findViewById(R.id.buttonContainer);
            backButton = findViewById(R.id.backButton);
            navigationHistory = new ArrayList<>();

            if (backButton != null) {
                backButton.setOnClickListener(v -> goBack());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khởi tạo: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupInitialScreen() {
        if (titleText != null) {
            titleText.setText("Chọn giới tính");
        }
        if (backButton != null) {
            backButton.setVisibility(View.GONE);
        }

        createButtons(new String[]{"Nam", "Nữ"}, "gender");
    }

    private void createButtons(String[] options, String category) {
        if (buttonContainer == null) {
            Toast.makeText(this, "ButtonContainer không tìm thấy!", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonContainer.removeAllViews();

        for (String option : options) {
            Button button = new Button(this);
            button.setText(option);

            // Tạo layout params
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(20, 10, 20, 10);
            button.setLayoutParams(params);

            // Style button
            button.setTextSize(16);
            button.setPadding(20, 40, 20, 40);

            // Set background tint instead of background resource
            button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFF9800));
            button.setTextColor(0xFFFFFFFF);

            button.setOnClickListener(v -> handleSelection(option, category));

            buttonContainer.addView(button);
        }
    }

    private void handleSelection(String selection, String category) {
        // Lưu lịch sử navigation
        navigationHistory.add(currentSelection);
        currentSelection += (currentSelection.isEmpty() ? "" : " -> ") + selection;

        switch (category) {
            case "gender":
                showMuscleGroups(selection);
                break;
            case "muscle_group":
                showExercises(selection);
                break;
            case "exercise":
                showExerciseDetails(selection);
                break;
            case "difficulty":
                showWorkoutPlan(selection);
                break;
        }
    }

    private void showMuscleGroups(String gender) {
        if (titleText != null) {
            titleText.setText("Chọn nhóm cơ muốn tập");
        }
        if (backButton != null) {
            backButton.setVisibility(View.VISIBLE);
        }

        String[] muscleGroups;
        if (gender.equals("Nam")) {
            muscleGroups = new String[]{"Ngực", "Lưng", "Vai", "Tay", "Chân", "Bụng"};
        } else {
            muscleGroups = new String[]{"Chân", "Mông", "Bụng", "Tay", "Lưng", "Yoga"};
        }

        createButtons(muscleGroups, "muscle_group");
    }

    private void showExercises(String muscleGroup) {
        if (titleText != null) {
            titleText.setText("Chọn bài tập " + muscleGroup);
        }

        String[] exercises;
        switch (muscleGroup) {
            case "Ngực":
                exercises = new String[]{"Push-up", "Bench Press", "Dumbbell Flyes", "Dips"};
                break;
            case "Lưng":
                exercises = new String[]{"Pull-up", "Lat Pulldown", "Rowing", "Deadlift"};
                break;
            case "Vai":
                exercises = new String[]{"Shoulder Press", "Lateral Raises", "Front Raises", "Shrugs"};
                break;
            case "Tay":
                exercises = new String[]{"Bicep Curls", "Tricep Dips", "Hammer Curls", "Close-grip Push-ups"};
                break;
            case "Chân":
                exercises = new String[]{"Squats", "Lunges", "Calf Raises", "Leg Press"};
                break;
            case "Bụng":
                exercises = new String[]{"Crunches", "Plank", "Russian Twists", "Leg Raises"};
                break;
            case "Mông":
                exercises = new String[]{"Hip Thrusts", "Glute Bridges", "Bulgarian Split Squats", "Clamshells"};
                break;
            case "Yoga":
                exercises = new String[]{"Sun Salutation", "Warrior Poses", "Tree Pose", "Downward Dog"};
                break;
            default:
                exercises = new String[]{"Bài tập cơ bản", "Bài tập nâng cao"};
        }

        createButtons(exercises, "exercise");
    }

    private void showExerciseDetails(String exercise) {
        if (titleText != null) {
            titleText.setText("Chọn mức độ khó");
        }

        String[] difficulties = new String[]{"Người mới bắt đầu", "Trung bình", "Nâng cao", "Chuyên nghiệp"};
        createButtons(difficulties, "difficulty");
    }

    private void showWorkoutPlan(String difficulty) {
        if (titleText != null) {
            titleText.setText("Kế hoạch tập luyện");
        }
        if (buttonContainer != null) {
            buttonContainer.removeAllViews();
        }

        // Tạo TextView hiển thị kế hoạch
        TextView planText = new TextView(this);
        planText.setText("🎯 Kế hoạch tập luyện của bạn:\n\n" +
                "📋 Lựa chọn: " + currentSelection + "\n" +
                "⏰ Thời gian: 30-45 phút\n" +
                "🔥 Mức độ: " + difficulty + "\n\n" +
                "Bạn đã sẵn sàng bắt đầu tập luyện!");

        planText.setTextSize(16);
        planText.setPadding(40, 40, 40, 40);
        planText.setBackgroundColor(0xFFE3F2FD);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 20, 20, 20);
        planText.setLayoutParams(params);

        if (buttonContainer != null) {
            buttonContainer.addView(planText);
        }

        // Nút bắt đầu tập luyện
        Button startButton = new Button(this);
        startButton.setText("Bắt đầu tập luyện");

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(20, 20, 20, 10);
        startButton.setLayoutParams(buttonParams);

        startButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF4CAF50));
        startButton.setTextColor(0xFFFFFFFF);

        startButton.setOnClickListener(v -> {
            Toast.makeText(this, "Bắt đầu tập luyện!", Toast.LENGTH_SHORT).show();

            String[] parts = currentSelection.split(" -> ");
            if (parts.length >= 3) {
                String gender = parts[0];
                String muscle = parts[1];
                String exercise = parts[2];

                UserSelection selection = new UserSelection(gender, muscle, exercise, difficulty);
                DatabaseHelper db = new DatabaseHelper(this);
                db.saveUserSelection(selection);
            }


            // Ở đây bạn có thể chuyển đến màn hình tập luyện
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        if (buttonContainer != null) {
            buttonContainer.addView(startButton);
        }

        // Nút tạo kế hoạch mới
        Button newPlanButton = new Button(this);
        newPlanButton.setText("Tạo kế hoạch mới");

        LinearLayout.LayoutParams newButtonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        newButtonParams.setMargins(20, 10, 20, 20);
        newPlanButton.setLayoutParams(newButtonParams);

        newPlanButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFFFF9800));
        newPlanButton.setTextColor(0xFFFFFFFF);

        newPlanButton.setOnClickListener(v -> {
            navigationHistory.clear();
            currentSelection = "";
            setupInitialScreen();
        });

        if (buttonContainer != null) {
            buttonContainer.addView(newPlanButton);
        }
    }

    private void goBack() {
        if (!navigationHistory.isEmpty()) {
            String lastSelection = navigationHistory.get(navigationHistory.size() - 1);
            navigationHistory.remove(navigationHistory.size() - 1);

            // Rebuild current selection string
            currentSelection = "";
            for (int i = 0; i < navigationHistory.size(); i++) {
                if (!navigationHistory.get(i).isEmpty()) {
                    currentSelection += (currentSelection.isEmpty() ? "" : " -> ") + navigationHistory.get(i);
                }
            }

            // Determine what screen to show based on navigation history
            if (navigationHistory.isEmpty()) {
                setupInitialScreen();
            } else {
                // Go back to appropriate screen based on history length
                switch (navigationHistory.size()) {
                    case 1:
                        showMuscleGroups(navigationHistory.get(0));
                        break;
                    case 2:
                        showExercises(navigationHistory.get(1));
                        break;
                    case 3:
                        showExerciseDetails(navigationHistory.get(2));
                        break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (backButton != null && backButton.getVisibility() == View.VISIBLE) {
            goBack();
        } else {
            super.onBackPressed();
        }
    }
}