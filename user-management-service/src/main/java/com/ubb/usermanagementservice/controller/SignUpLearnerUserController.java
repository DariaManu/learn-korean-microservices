package com.ubb.usermanagementservice.controller;

import com.ubb.usermanagementservice.controller.request.SignUpRequest;
import com.ubb.usermanagementservice.controller.response.UserRegisteredResponse;
import com.ubb.usermanagementservice.model.exception.LearnerUserEmailTakenException;
import com.ubb.usermanagementservice.model.exception.LearnerUserUsernameTakenException;
import com.ubb.usermanagementservice.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
@RequiredArgsConstructor
public class SignUpLearnerUserController {
    private final UserManagementService userManagementService;

    @PostMapping("/register")
    public ResponseEntity<?> signUpLearnerUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            UserRegisteredResponse userRegisteredResponse = userManagementService.signUpLearnerUser(signUpRequest);
            return ResponseEntity.ok(userRegisteredResponse);
        } catch (LearnerUserEmailTakenException | LearnerUserUsernameTakenException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
