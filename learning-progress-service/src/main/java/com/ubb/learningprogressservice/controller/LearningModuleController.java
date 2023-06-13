package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.controller.request.CheckLearningModuleAccessRequest;
import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/modules")
@RequiredArgsConstructor
public class LearningModuleController {
    private final LearningProgressService learningProgressService;

    @PostMapping()
    public ResponseEntity<?> checkAccessToLearningModule(@RequestBody final CheckLearningModuleAccessRequest request) {
        if (learningProgressService.checkAccessToLearningModule(request.getLearningModuleName(), request.getLearnerUserId())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping()
    public List<String> getLearningModuleNames() {
        return learningProgressService.getLearningModuleNames();
    }
}
