package com.ubb.blogservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    @Column(columnDefinition = "text")
    private final String text;
    private LocalDateTime datePosted;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {
        this(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return commentId != null && Objects.equals(commentId, comment.commentId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
