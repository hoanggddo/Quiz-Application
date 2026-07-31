package com.hoangdo.quizapp.repository;

import com.hoangdo.quizapp.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    // Spring Data JPA derives the query from the method name --
    // no hand-written SQL, and it stays type-safe.
    List<Score> findTop10ByOrderByPointsScoredDesc();
}
