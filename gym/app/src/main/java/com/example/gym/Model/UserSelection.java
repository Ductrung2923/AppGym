package com.example.gym.Model;

public class UserSelection {
    private int id;
    private String gender;
    private String muscleGroup;
    private String exercise;
    private String difficulty;
    private long timestamp; // thời gian tạo kế hoạch

    public UserSelection(){

    }

    public UserSelection(String gender, String muscleGroup, String exercise, String difficulty) {
        this.gender = gender;
        this.muscleGroup = muscleGroup;
        this.exercise = exercise;
        this.difficulty = difficulty;
        this.timestamp = System.currentTimeMillis();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
