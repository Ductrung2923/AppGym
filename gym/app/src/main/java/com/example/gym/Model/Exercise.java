package com.example.gym.Model;

public class Exercise {
    private String name;
    private String sets;
    private int imageResId;
    private int targetResId;

    public Exercise(String name, String sets, int imageResId, int targetResId) {
        this.name = name;
        this.sets = sets;
        this.imageResId = imageResId;
        this.targetResId = targetResId;
    }

    public String getName() {
        return name;
    }

    public String getSets() {
        return sets;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getTargetResId() {
        return targetResId;
    }
}
