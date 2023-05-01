package com.ubb.vocabularyservice.service;

import com.ubb.vocabularyservice.model.Vocabulary;
import com.ubb.vocabularyservice.repository.VocabularyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VocabularyService {
    private final VocabularyRepository vocabularyRepository;

    public List<Vocabulary> findAllByWord(final String word) {
        return vocabularyRepository.findAllByWord(word);
    }
}
