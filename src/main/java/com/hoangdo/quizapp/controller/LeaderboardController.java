package com.hoangdo.quizapp.controller;

import com.hoangdo.quizapp.dto.ScoreSubmissionRequest;
import com.hoangdo.quizapp.model.Score;
import com.hoangdo.quizapp.service.LeaderboardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping
    public ResponseEntity<Score> submitScore(@Valid @RequestBody ScoreSubmissionRequest request) {
        Score saved = leaderboardService.submitScore(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Score> getLeaderboard() {
        return leaderboardService.getTopScores();
    }
}
