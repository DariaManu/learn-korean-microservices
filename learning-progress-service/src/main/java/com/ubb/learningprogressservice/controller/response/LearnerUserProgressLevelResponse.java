package com.ubb.learningprogressservice.controller.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class LearnerUserProgressLevelResponse {
    private final String learnerUserProgressLevel;
}
