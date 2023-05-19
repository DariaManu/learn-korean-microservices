package com.ubb.learningprogressservice.controller.request;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class NewUserProgressRequest {
    private Long learnerUserId;
}
