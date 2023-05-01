package com.ubb.usermanagementservice.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginRequest {
    private final String email;
    private final String password;
}
