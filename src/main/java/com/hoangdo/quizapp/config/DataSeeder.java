package com.hoangdo.quizapp.config;

import com.hoangdo.quizapp.model.Question;
import com.hoangdo.quizapp.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds a handful of starter questions on first run.
 *
 * Replaces the earlier data.sql approach. A CommandLineRunner bean is
 * guaranteed by Spring's own lifecycle to run only after the full
 * application context (including Hibernate's schema creation) has
 * finished initializing, so there's no risk of this running before the
 * "questions" table actually has all its columns. It also avoids the
 * file-encoding issues that come with plain .sql text files, since this
 * is a normal UTF-8 Java source file.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final QuestionRepository questionRepository;

    public DataSeeder(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        if (questionRepository.count() > 0) {
            return; // already seeded from a previous run
        }

        questionRepository.save(new Question(
                "Vocabulary", "What does \"xin chào\" mean?",
                "Goodbye", "Hello", "Thank you", "Please", "B"
        ));

        questionRepository.save(new Question(
                "Vocabulary", "What does \"cảm ơn\" mean?",
                "Sorry", "Please", "Thank you", "Yes", "C"
        ));

        questionRepository.save(new Question(
                "Grammar", "Which word means \"and\" in Vietnamese?",
                "và", "nhưng", "hoặc", "vì", "A"
        ));

        questionRepository.save(new Question(
                "History & Culture", "What is the capital of Vietnam?",
                "Ho Chi Minh City", "Da Nang", "Hanoi", "Hue", "C"
        ));
    }
}
