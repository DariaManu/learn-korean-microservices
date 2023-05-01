package com.ubb.learningprogressservice.repository;

import com.ubb.learningprogressservice.model.LearningModule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LearningModuleRepository extends CrudRepository<LearningModule, Long> {
    public LearningModule findByName(final String learningModuleName);
}
