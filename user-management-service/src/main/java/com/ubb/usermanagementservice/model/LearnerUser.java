package com.ubb.usermanagementservice.model;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "learner_user")
public class LearnerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long learnerUserId;

    private final String email;
    private final String password;
    private final String username;

    public LearnerUser() {
        this(null, null, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LearnerUser that = (LearnerUser) o;
        return learnerUserId != null && Objects.equals(learnerUserId, that.learnerUserId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
