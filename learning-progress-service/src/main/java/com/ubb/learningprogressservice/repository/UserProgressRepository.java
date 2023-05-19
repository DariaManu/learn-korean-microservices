package com.ubb.learningprogressservice.repository;

import com.ubb.learningprogressservice.model.UserProgress;
import org.springframework.data.repository.CrudRepository;

public interface UserProgressRepository extends CrudRepository<UserProgress, Long> {
    public UserProgress findByUserId(Long userId);
}
