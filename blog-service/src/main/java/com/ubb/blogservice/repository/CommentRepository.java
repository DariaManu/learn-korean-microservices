package com.ubb.blogservice.repository;

import com.ubb.blogservice.model.Comment;
import com.ubb.blogservice.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>, PagingAndSortingRepository<Comment, Long> {
    public List<Comment> findNextByPost(final Post post, final PageRequest pageRequest);
    public List<Comment> findAllByPost(final Post post);
}
