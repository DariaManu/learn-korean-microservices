package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.model.QuizAttempt;
import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quiz/history")
@RequiredArgsConstructor
public class GetQuizAttemptHistoryController {
    private final LearningProgressService learningProgressService;

    @GetMapping("/{learningModuleName}/{userId}")
    public List<QuizAttempt> getQuizAttemptHistory(@PathVariable final String learningModuleName, @PathVariable final Long userId) {
        return learningProgressService.getQuizAttemptHistoryForUserAndLearningModule(userId, learningModuleName);
    }
}
