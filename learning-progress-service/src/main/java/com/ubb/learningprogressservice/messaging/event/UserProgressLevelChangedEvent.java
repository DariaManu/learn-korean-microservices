package com.ubb.learningprogressservice.messaging.event;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class UserProgressLevelChangedEvent implements Serializable {
    private Long learnerUserId;
    private String newUserProgressLevel;
}
