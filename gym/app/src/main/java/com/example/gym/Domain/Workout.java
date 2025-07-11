package com.example.gym.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Workout implements Serializable {

    private String title;
    private String description;
    private String picPath;
    private int kcal;
    private String durationAll;
    private ArrayList<Lession> lessions;
    private String level; // ✅ Thêm thuộc tính cấp độ

    // ✅ Constructor mới có thêm tham số `level`
    public Workout(String title, String description, String picPath, int kcal, String durationAll, ArrayList<Lession> lessions, String level) {
        this.title = title;
        this.description = description;
        this.picPath = picPath;
        this.kcal = kcal;
        this.durationAll = durationAll;
        this.lessions = lessions;
        this.level = level;
    }

    // ✅ Getter và Setter cho `level`
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    // Các getter/setter còn lại giữ nguyên
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getDurationAll() {
        return durationAll;
    }

    public void setDurationAll(String durationAll) {
        this.durationAll = durationAll;
    }

    public ArrayList<Lession> getLessions() {
        return lessions;
    }

    public void setLessions(ArrayList<Lession> lessions) {
        this.lessions = lessions;
    }
}
