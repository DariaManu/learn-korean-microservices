package com.ubb.usermanagementservice.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserRegisteredResponse {
    private final Long learnerUserId;
    private final String username;
    private final String userProgressLevel;
}
