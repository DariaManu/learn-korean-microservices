package com.ubb.vocabularyservice.controller;

import com.ubb.vocabularyservice.model.Vocabulary;
import com.ubb.vocabularyservice.service.VocabularyService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
@RequestMapping("/vocabulary")
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @GetMapping("/{word}")
    public List<Vocabulary> getTranslationForWord(@PathVariable final String word) {
        return vocabularyService.findAllByWord(word);
    }
}
