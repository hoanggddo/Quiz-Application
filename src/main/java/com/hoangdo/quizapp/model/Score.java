package com.hoangdo.quizapp.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "scores")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int pointsScored;

    @Column(nullable = false)
    private int totalQuestions;

    @Column(nullable = false)
    private Instant submittedAt;

    public Score() {
    }

    public Score(String username, String category, int pointsScored, int totalQuestions) {
        this.username = username;
        this.category = category;
        this.pointsScored = pointsScored;
        this.totalQuestions = totalQuestions;
        this.submittedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCategory() {
        return category;
    }

    public int getPointsScored() {
        return pointsScored;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }
}
