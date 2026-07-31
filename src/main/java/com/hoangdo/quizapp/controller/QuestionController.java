package com.hoangdo.quizapp.controller;

import com.hoangdo.quizapp.dto.QuestionResponse;
import com.hoangdo.quizapp.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<QuestionResponse> getQuestions(@RequestParam String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/{id}/answer")
    public Map<String, Boolean> submitAnswer(@PathVariable Long id, @RequestParam String option) {
        boolean correct = questionService.isCorrect(id, option);
        return Map.of("correct", correct);
    }
}
