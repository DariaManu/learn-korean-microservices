package com.ubb.blogservice.repository;

import com.ubb.blogservice.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends CrudRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
    public Post findByPostId(final Long postId);
}
