package com.ubb.usermanagementservice.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterRequest {
    private final String email;
    private final String password;
    private final String username;
}
