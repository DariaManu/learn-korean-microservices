package com.ubb.learningprogressservice.repository;

import com.ubb.learningprogressservice.model.LearningModule;
import com.ubb.learningprogressservice.model.ProgressLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningModuleRepository extends CrudRepository<LearningModule, Long> {
    public LearningModule findByName(final String learningModuleName);
    public List<LearningModule> findAllByRequiredProgressLevel(final ProgressLevel progressLevel);
}
