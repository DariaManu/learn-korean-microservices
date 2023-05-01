package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.model.Question;
import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("question")
@RequiredArgsConstructor
public class GetQuestionsController {
    private final LearningProgressService learningProgressService;

    @GetMapping("/{learningModuleName}")
    public List<Question> getQuestionsForLearningModule(@PathVariable final String learningModuleName) {
        return learningProgressService.getRandomQuestionsForLearningModule(learningModuleName);
    }
}
