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
            Toast.makeText(this, "Error create: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupInitialScreen() {
        if (titleText != null) {
            titleText.setText("Gender");
        }
        if (backButton != null) {
            backButton.setVisibility(View.GONE);
        }

        createButtons(new String[]{"Male", "Female"}, "gender");
    }

    private void createButtons(String[] options, String category) {
        if (buttonContainer == null) {
            Toast.makeText(this, "Cannot see ButtonContainer!", Toast.LENGTH_SHORT).show();
            return;
        }

        buttonContainer.removeAllViews();

        for (String option : options) {
            Button button = new Button(this);
            button.setText(option);

            // Create layout params
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
        // Save navigation history
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
            titleText.setText("Choose Muscle Group");
        }
        if (backButton != null) {
            backButton.setVisibility(View.VISIBLE);
        }

        String[] muscleGroups;
        if (gender.equals("Male")) {
            muscleGroups = new String[]{"Chest", "Back", "Shoulders", "Arms", "Legs", "Abs"};
        } else {
            muscleGroups = new String[]{"Legs", "Glutes", "Abs", "Arms", "Back", "Yoga"};
        }

        createButtons(muscleGroups, "muscle_group");
    }

    private void showExercises(String muscleGroup) {
        if (titleText != null) {
            titleText.setText("Choose " + muscleGroup + " Exercise");
        }

        String[] exercises;
        switch (muscleGroup) {
            case "Chest":
                exercises = new String[]{"Push-up", "Bench Press", "Dumbbell Flyes", "Dips"};
                break;
            case "Back":
                exercises = new String[]{"Pull-up", "Lat Pulldown", "Rowing", "Deadlift"};
                break;
            case "Shoulders":
                exercises = new String[]{"Shoulder Press", "Lateral Raises", "Front Raises", "Shrugs"};
                break;
            case "Arms":
                exercises = new String[]{"Bicep Curls", "Tricep Dips", "Hammer Curls", "Close-grip Push-ups"};
                break;
            case "Legs":
                exercises = new String[]{"Squats", "Lunges", "Calf Raises", "Leg Press"};
                break;
            case "Abs":
                exercises = new String[]{"Crunches", "Plank", "Russian Twists", "Leg Raises"};
                break;
            case "Glutes":
                exercises = new String[]{"Hip Thrusts", "Glute Bridges", "Bulgarian Split Squats", "Clamshells"};
                break;
            case "Yoga":
                exercises = new String[]{"Sun Salutation", "Warrior Poses", "Tree Pose", "Downward Dog"};
                break;
            default:
                exercises = new String[]{"Basic Exercise", "Advanced Exercise"};
        }

        createButtons(exercises, "exercise");
    }

    private void showExerciseDetails(String exercise) {
        if (titleText != null) {
            titleText.setText("Choose Difficulty Level");
        }

        String[] difficulties = new String[]{"Beginner", "Intermediate", "Advanced", "Professional"};
        createButtons(difficulties, "difficulty");
    }

    private void showWorkoutPlan(String difficulty) {
        if (titleText != null) {
            titleText.setText("Workout Plan");
        }
        if (buttonContainer != null) {
            buttonContainer.removeAllViews();
        }

        // Determine workout time based on difficulty level
        String workoutTime;
        switch (difficulty) {
            case "Beginner":
                workoutTime = "15-20 minutes";
                break;
            case "Intermediate":
                workoutTime = "25-30 minutes";
                break;
            case "Advanced":
                workoutTime = "35-40 minutes";
                break;
            case "Professional":
                workoutTime = "40-45 minutes";
                break;
            default:
                workoutTime = "30-45 minutes";
        }

        // Create TextView to display plan
        TextView planText = new TextView(this);
        planText.setText("🎯 Your Workout Plan:\n\n" +
                "📋 Selection: " + currentSelection + "\n" +
                "⏰ Duration: " + workoutTime + "\n" +
                "🔥 Level: " + difficulty + "\n\n" +
                "Are you ready to start your workout?");

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

        // Start workout button
        Button startButton = new Button(this);
        startButton.setText("Start Workout");

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.setMargins(20, 20, 20, 10);
        startButton.setLayoutParams(buttonParams);

        startButton.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF4CAF50));
        startButton.setTextColor(0xFFFFFFFF);

        startButton.setOnClickListener(v -> {
            Toast.makeText(this, "Starting workout!", Toast.LENGTH_SHORT).show();

            String[] parts = currentSelection.split(" -> ");
            if (parts.length >= 3) {
                String gender = parts[0];
                String muscle = parts[1];
                String exercise = parts[2];

                UserSelection selection = new UserSelection(gender, muscle, exercise, difficulty);
                DatabaseHelper db = new DatabaseHelper(this);
                db.saveUserSelection(selection);
            }

            // Here you can navigate to workout screen
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        if (buttonContainer != null) {
            buttonContainer.addView(startButton);
        }

        // Create new plan button
        Button newPlanButton = new Button(this);
        newPlanButton.setText("Create New Plan");

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