package com.example.gym.Model;

public class Program {
    private String tagline;
    private String subtitle;
    private int imageResId;

    public Program(String tagline, String subtitle, int imageResId) {
        this.tagline = tagline;
        this.subtitle = subtitle;
        this.imageResId = imageResId;
    }

    public String getTagline() {
        return tagline;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImageResId() {
        return imageResId;
    }
}
