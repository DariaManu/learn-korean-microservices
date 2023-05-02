package com.ubb.learningprogressservice.controller.request;

import com.ubb.learningprogressservice.model.UserAnswer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class QuizAttemptRequest {
    private final Long userId;
    private final String learningModuleName;
    private final List<UserAnswer> userAnswers;
}
