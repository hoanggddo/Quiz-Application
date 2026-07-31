package com.hoangdo.quizapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ScoreSubmissionRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Category is required")
    private String category;

    @Min(value = 0, message = "Points scored cannot be negative")
    private int pointsScored;

    @Min(value = 1, message = "Total questions must be at least 1")
    private int totalQuestions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(int pointsScored) {
        this.pointsScored = pointsScored;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
