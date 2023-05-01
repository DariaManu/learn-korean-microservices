package com.ubb.usermanagementservice.controller;

import com.ubb.usermanagementservice.controller.request.LoginRequest;
import com.ubb.usermanagementservice.model.exception.LearnerUserIncorrectPasswordException;
import com.ubb.usermanagementservice.model.exception.LearnerUserNotFoundException;
import com.ubb.usermanagementservice.service.LearnerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
@RequiredArgsConstructor
public class LoginLearnerUserController {
    private final LearnerUserService learnerUserService;

    @PostMapping("/login")
    public ResponseEntity<?> loginLearnerUser(@RequestBody final LoginRequest loginRequest) {
        try {
            final Long userId = learnerUserService.loginLearnerUser(loginRequest);
            return ResponseEntity.ok(userId);
        } catch (LearnerUserNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (LearnerUserIncorrectPasswordException exception) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
