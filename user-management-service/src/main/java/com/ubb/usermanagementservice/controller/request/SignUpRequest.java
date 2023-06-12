package com.ubb.usermanagementservice.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SignUpRequest {
    private final String email;
    private final String password;
    private final String username;
}
