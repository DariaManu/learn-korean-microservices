package com.ubb.learningprogressservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "user_progress")
public class UserProgress {
    @Id
    private final Long userId;
    @Enumerated(EnumType.STRING)
    private final ProgressLevel level;

    public UserProgress() {
        this(0L, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserProgress that = (UserProgress) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
