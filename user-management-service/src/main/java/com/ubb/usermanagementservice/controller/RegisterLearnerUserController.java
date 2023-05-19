package com.ubb.usermanagementservice.controller;

import com.ubb.usermanagementservice.controller.request.RegisterRequest;
import com.ubb.usermanagementservice.controller.response.UserRegisteredResponse;
import com.ubb.usermanagementservice.model.exception.LearnerUserEmailTakenException;
import com.ubb.usermanagementservice.model.exception.LearnerUserUsernameTakenException;
import com.ubb.usermanagementservice.service.LearnerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
@RequiredArgsConstructor
public class RegisterLearnerUserController {
    private final LearnerUserService learnerUserService;

    @PostMapping("/register")
    public ResponseEntity<?> registerLearnerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            UserRegisteredResponse userRegisteredResponse = learnerUserService.registerLearnerUser(registerRequest);
            return ResponseEntity.ok(userRegisteredResponse);
        } catch (LearnerUserEmailTakenException | LearnerUserUsernameTakenException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
