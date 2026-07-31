package com.hoangdo.quizapp.service;

import com.hoangdo.quizapp.dto.QuestionResponse;
import com.hoangdo.quizapp.exception.ApiException;
import com.hoangdo.quizapp.model.Question;
import com.hoangdo.quizapp.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionResponse> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category).stream()
                .map(QuestionResponse::fromEntity)
                .toList();
    }

    // Grades one answer server-side, so the answer key never has to be
    // trusted to (or exposed by) the client.
    public boolean isCorrect(Long questionId, String submittedOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiException("Question not found", HttpStatus.NOT_FOUND));
        return question.getCorrectOption().equalsIgnoreCase(submittedOption);
    }
}
