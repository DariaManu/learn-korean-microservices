package com.ubb.learningprogressservice.repository;

import com.ubb.learningprogressservice.model.LearningModule;
import com.ubb.learningprogressservice.model.QuizAttempt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends CrudRepository<QuizAttempt, Long> {
    public List<QuizAttempt> findByLearningModuleAndUserId(final LearningModule learningModule, final Long userId);
}
