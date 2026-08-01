package com.hoangdo.quizapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category; // e.g. Vocabulary, Grammar, History & Culture

    @Column(nullable = false, length = 1000)
    private String questionText;

    @Column(nullable = false)
    private String optionA;

    @Column(nullable = false)
    private String optionB;

    @Column(nullable = false)
    private String optionC;

    @Column(nullable = false)
    private String optionD;

    // Stored as "A", "B", "C", or "D" -- never exposed to the client
    // via the DTO used for fetching questions (see QuestionResponse).
    @Column(nullable = false)
    private String correctOption;

    @Column(length = 500)
    private String hint;

    public Question() {
    }

    public Question(String category, String questionText, String optionA, String optionB,
                     String optionC, String optionD, String correctOption, String hint) {
        this.category = category;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.hint = hint;
    }

    public Long getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public String getHint() {
        return hint;
    }
}
