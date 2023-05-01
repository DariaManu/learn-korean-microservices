package com.ubb.learningprogressservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Embeddable
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;
    private final String questionText;
    @ElementCollection
    private final List<QuestionAnswer> questionAnswers;
    private final int correctAnswerIndex;

    public Question() {
        this(null, null, 0);
    }
}
