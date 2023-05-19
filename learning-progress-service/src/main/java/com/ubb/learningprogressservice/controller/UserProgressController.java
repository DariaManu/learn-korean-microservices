package com.ubb.learningprogressservice.controller;

import com.ubb.learningprogressservice.controller.request.NewUserProgressRequest;
import com.ubb.learningprogressservice.controller.response.LearnerUserProgressLevelResponse;
import com.ubb.learningprogressservice.service.LearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/progress")
@RequiredArgsConstructor
public class UserProgressController {
    private final LearningProgressService learningProgressService;

    @PostMapping()
    public LearnerUserProgressLevelResponse addUserProgressForNewLearnerUser(@RequestBody NewUserProgressRequest newUserProgressRequest) {
        final String learnerUserProgressLevel = learningProgressService.addUserProgressForNewLearnerUser(newUserProgressRequest.getLearnerUserId());
        return new LearnerUserProgressLevelResponse(learnerUserProgressLevel);
    }

    @GetMapping("/{learnerUserId}")
    public LearnerUserProgressLevelResponse getUserProgressLevel(@PathVariable final Long learnerUserId) {
        final String learnerUserProgressLevel = learningProgressService.getUserProgressLevel(learnerUserId);
        return new LearnerUserProgressLevelResponse(learnerUserProgressLevel);
    }
}
