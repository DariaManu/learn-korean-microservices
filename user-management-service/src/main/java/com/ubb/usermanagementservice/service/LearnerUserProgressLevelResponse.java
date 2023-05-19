package com.ubb.usermanagementservice.service;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class LearnerUserProgressLevelResponse implements Serializable {
    private String learnerUserProgressLevel;
}
