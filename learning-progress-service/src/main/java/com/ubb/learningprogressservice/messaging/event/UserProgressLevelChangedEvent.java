package com.ubb.learningprogressservice.messaging.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserProgressLevelChangedEvent implements Serializable {
    private final Long learnerUserId;
    private final String newUserProgressLevel;
}
