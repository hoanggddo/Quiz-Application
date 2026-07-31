package com.hoangdo.quizapp.dto;

import com.hoangdo.quizapp.model.Question;

/**
 * Client-facing view of a Question. Deliberately excludes correctOption --
 * the API should never hand the answer key to the quiz-taker.
 */
public class QuestionResponse {

    private Long id;
    private String category;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public static QuestionResponse fromEntity(Question q) {
        QuestionResponse r = new QuestionResponse();
        r.id = q.getId();
        r.category = q.getCategory();
        r.questionText = q.getQuestionText();
        r.optionA = q.getOptionA();
        r.optionB = q.getOptionB();
        r.optionC = q.getOptionC();
        r.optionD = q.getOptionD();
        return r;
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
}
