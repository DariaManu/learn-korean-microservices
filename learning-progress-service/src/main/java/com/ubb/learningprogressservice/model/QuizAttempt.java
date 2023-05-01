package com.ubb.learningprogressservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "quiz_attempt")
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizAttemptId;
    private final Long userId;
    @ManyToOne
    @JoinColumn(name = "learning_module_id")
    private final LearningModule learningModule;
    @ElementCollection
    private final List<UserAnswer> userAnswers;
    private int score;
    private boolean quizPassed;

    public QuizAttempt() {
        this(null, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuizAttempt that = (QuizAttempt) o;
        return quizAttemptId != null && Objects.equals(quizAttemptId, that.quizAttemptId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
