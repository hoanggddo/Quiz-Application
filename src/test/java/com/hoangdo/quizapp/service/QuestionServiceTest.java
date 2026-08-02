package com.hoangdo.quizapp.service;

import com.hoangdo.quizapp.exception.ApiException;
import com.hoangdo.quizapp.model.Question;
import com.hoangdo.quizapp.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QuestionServiceTest {

    private QuestionRepository questionRepository;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        questionService = new QuestionService(questionRepository);
    }

    @Test
    void isCorrect_returnsTrueForMatchingAnswer_caseInsensitive() {
        Question q = new Question("Vocabulary", "Q?", "A opt", "B opt", "C opt", "D opt", "B", "a hint");
        when(questionRepository.findById(1L)).thenReturn(Optional.of(q));

        assertThat(questionService.isCorrect(1L, "b")).isTrue();
        assertThat(questionService.isCorrect(1L, "B")).isTrue();
    }

    @Test
    void isCorrect_returnsFalseForWrongAnswer() {
        Question q = new Question("Vocabulary", "Q?", "A opt", "B opt", "C opt", "D opt", "B", "a hint");
        when(questionRepository.findById(1L)).thenReturn(Optional.of(q));

        assertThat(questionService.isCorrect(1L, "A")).isFalse();
    }

    @Test
    void isCorrect_throwsWhenQuestionDoesNotExist() {
        when(questionRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> questionService.isCorrect(999L, "A"))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining("not found");
    }
}
