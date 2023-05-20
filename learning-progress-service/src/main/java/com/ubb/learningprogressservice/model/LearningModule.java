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
@Table(name = "learning_module")
public class LearningModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_module_id")
    private Long learningModuleId;
    private final String name;
    @Enumerated(EnumType.STRING)
    private final ProgressLevel requiredProgressLevel;

    @ElementCollection
    private final List<Question> questions;

    public LearningModule() {
        this(null, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LearningModule that = (LearningModule) o;
        return learningModuleId != null && Objects.equals(learningModuleId, that.learningModuleId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
