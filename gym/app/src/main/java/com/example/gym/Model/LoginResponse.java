package com.example.gym.Model;

public class LoginResponse {
    private int userId;
    private String fullName;
    private String email;
    private int role;
    private String token;

    // Getters
    public String getFullName() {
        return fullName;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

