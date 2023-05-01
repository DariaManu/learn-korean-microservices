package com.ubb.vocabularyservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "vocabulary")
public class Vocabulary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vocabularyId;
    private String word;
    private String translation;
    private String meaning;

    public Vocabulary() {
        this(0L, null, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vocabulary that)) return false;
        return Objects.equals(vocabularyId, that.vocabularyId) && Objects.equals(word, that.word) && Objects.equals(translation, that.translation) && Objects.equals(meaning, that.meaning);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vocabularyId, word, translation, meaning);
    }
}
