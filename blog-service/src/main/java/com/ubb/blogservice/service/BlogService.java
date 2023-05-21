package com.ubb.blogservice.service;

import com.ubb.blogservice.controller.response.PostResponseEntity;
import com.ubb.blogservice.model.Post;
import com.ubb.blogservice.repository.CommentRepository;
import com.ubb.blogservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private static final int PAGE_SIZE = 3;
    private static final String DATE_TIME_PATTERN = "d MMM yyyy  hh:mm a";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public Integer getNumberOfPosts() {
        return StreamSupport.stream(postRepository.findAll().spliterator(), false).toList().size();
    }

    public List<PostResponseEntity> getNextPosts(final int pageNumber) {
        Page<Post> pages = postRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE));
        return pages.getContent().stream().map((post) -> new PostResponseEntity(
                post.getPostId(),
                post.getText(),
                post.getDatePosted().format(DATE_TIME_FORMATTER)
        )).toList();
    }

    public Post addPost(Post post) {
        final LocalDateTime currentTime = LocalDateTime.now();
        post.setDatePosted(currentTime);
        return postRepository.save(post);
    }
}
