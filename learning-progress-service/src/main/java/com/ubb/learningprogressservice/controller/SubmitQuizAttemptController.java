package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.controller.request.QuizAttemptRequest;
import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class SubmitQuizAttemptController {
    private final LearningProgressService learningProgressService;

    @PostMapping("/attempt")
    public ResponseEntity<?> submitQuizAttempt(@RequestBody QuizAttemptRequest request) {
        System.out.println(request);
        learningProgressService.submitUserAnswersForQuiz(request.getUserId(), request.getLearningModuleName(), request.getUserAnswers());
        return ResponseEntity.ok(1);
    }
}
