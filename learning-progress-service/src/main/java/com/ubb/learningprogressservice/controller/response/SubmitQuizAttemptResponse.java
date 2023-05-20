package com.ubb.learningprogressservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SubmitQuizAttemptResponse {
    private boolean quizPassed;
    private int score;
}
