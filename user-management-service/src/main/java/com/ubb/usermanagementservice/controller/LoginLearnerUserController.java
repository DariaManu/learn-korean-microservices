package com.ubb.usermanagementservice.controller;

import com.ubb.usermanagementservice.controller.request.LoginRequest;
import com.ubb.usermanagementservice.controller.response.UserRegisteredResponse;
import com.ubb.usermanagementservice.model.exception.LearnerUserIncorrectPasswordException;
import com.ubb.usermanagementservice.model.exception.LearnerUserNotFoundException;
import com.ubb.usermanagementservice.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
@RequiredArgsConstructor
public class LoginLearnerUserController {
    private final UserManagementService userManagementService;

    @PostMapping("/login")
    public ResponseEntity<?> loginLearnerUser(@RequestBody final LoginRequest loginRequest) {
        try {
            final UserRegisteredResponse userRegisteredResponse = userManagementService.loginLearnerUser(loginRequest);
            return ResponseEntity.ok(userRegisteredResponse);
        } catch (LearnerUserNotFoundException | LearnerUserIncorrectPasswordException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
