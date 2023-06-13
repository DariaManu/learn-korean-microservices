package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.controller.request.QuizAttemptRequest;
import com.ubb.learningprogressservice.controller.response.SubmitQuizAttemptResponse;
import com.ubb.learningprogressservice.model.Question;
import com.ubb.learningprogressservice.model.QuizAttempt;
import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final LearningProgressService learningProgressService;

    @GetMapping("/questions/{learningModuleName}")
    public List<Question> getQuestionsForLearningModule(@PathVariable final String learningModuleName) {
        return learningProgressService.getRandomQuestionsForLearningModule(learningModuleName);
    }

    @PostMapping("/attempt")
    public ResponseEntity<?> submitQuizAttempt(@RequestBody QuizAttemptRequest request) {
        System.out.println(request);
        final SubmitQuizAttemptResponse response = learningProgressService.submitUserAnswersForQuiz(request.getUserId(), request.getLearningModuleName(), request.getUserAnswers());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{learningModuleName}/{userId}")
    public List<QuizAttempt> getQuizAttemptHistory(@PathVariable final String learningModuleName, @PathVariable final Long userId) {
        return learningProgressService.getQuizAttemptHistoryForUserAndLearningModule(userId, learningModuleName);
    }
}
