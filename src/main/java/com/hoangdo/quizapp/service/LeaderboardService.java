package com.hoangdo.quizapp.service;

import com.hoangdo.quizapp.dto.ScoreSubmissionRequest;
import com.hoangdo.quizapp.model.Score;
import com.hoangdo.quizapp.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final ScoreRepository scoreRepository;

    public LeaderboardService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public Score submitScore(ScoreSubmissionRequest request) {
        Score score = new Score(
                request.getUsername(),
                request.getCategory(),
                request.getPointsScored(),
                request.getTotalQuestions()
        );
        return scoreRepository.save(score);
    }

    public List<Score> getTopScores() {
        return scoreRepository.findTop10ByOrderByPointsScoredDesc();
    }
}
