package com.ubb.learningprogressservice.repository;

import com.ubb.learningprogressservice.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
}
