package com.ubb.learningprogressservice.controller.request;

import com.ubb.learningprogressservice.model.ProgressLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CheckLearningModuleAccessRequest {
    private String learningModuleName;
    private Long learnerUserId;
}
