package com.ubb.vocabularyservice.repository;

import com.ubb.vocabularyservice.model.Vocabulary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyRepository extends CrudRepository<Vocabulary, Long> {
    public List<Vocabulary> findAllByWord(final String word);
}
