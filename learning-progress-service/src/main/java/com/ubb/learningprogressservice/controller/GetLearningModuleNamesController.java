package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/module")
@RequiredArgsConstructor
public class GetLearningModuleNamesController {
    private final LearningProgressService learningProgressService;

    @GetMapping()
    public List<String> getLearningModuleNames() {
        return learningProgressService.getLearningModuleNames();
    }
}
