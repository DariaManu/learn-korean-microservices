package com.ubb.learningprogressservice.repository;

import com.ubb.learningprogressservice.model.UserAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Long> {
}
