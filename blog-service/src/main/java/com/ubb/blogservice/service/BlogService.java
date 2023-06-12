package com.ubb.blogservice.service;

import com.ubb.blogservice.controller.response.CommentResponseEntity;
import com.ubb.blogservice.controller.response.PostResponseEntity;
import com.ubb.blogservice.model.Comment;
import com.ubb.blogservice.model.Post;
import com.ubb.blogservice.repository.CommentsRepository;
import com.ubb.blogservice.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private static final int PAGE_SIZE = 3;
    private static final String DATE_TIME_PATTERN = "d MMM yyyy  hh:mm a";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public Integer getNumberOfPosts() {
        return StreamSupport.stream(postsRepository.findAll().spliterator(), false).toList().size();
    }

    public List<PostResponseEntity> getNextPosts(final int pageNumber) {
        Page<Post> pages = postsRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("datePosted").descending()));
        return pages.getContent().stream().map((post) -> new PostResponseEntity(
                post.getPostId(),
                post.getText(),
                post.getDatePosted().format(DATE_TIME_FORMATTER)
        )).toList();
    }

    public PostResponseEntity addPost(Post post) {
        final LocalDateTime currentTime = LocalDateTime.now();
        post.setDatePosted(currentTime);
        final Post savedPost = postsRepository.save(post);
        return new PostResponseEntity(
                savedPost.getPostId(),
                savedPost.getText(),
                savedPost.getDatePosted().format(DATE_TIME_FORMATTER)
        );
    }

    public Integer getNumberOfCommentsForPost(final Long postId) {
        final Post post = postsRepository.findByPostId(postId);
        return commentsRepository.findAllByPost(post).size();
    }

    public List<CommentResponseEntity> getNextCommentsForPost(final Long postId, final int pageNumber) {
        final Post post = postsRepository.findByPostId(postId);
        List<Comment> comments = commentsRepository.findNextByPost(post,
                PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("datePosted").descending()));
        return comments.stream().map((comment) -> new CommentResponseEntity(
                comment.getCommentId(),
                comment.getText(),
                comment.getDatePosted().format(DATE_TIME_FORMATTER)
        )).toList();
    }

    public CommentResponseEntity addComment(final Long postId, final Comment comment) {
        final Post post = postsRepository.findByPostId(postId);
        final LocalDateTime currentDateTime = LocalDateTime.now();
        comment.setDatePosted(currentDateTime);
        comment.setPost(post);
        final Comment savedComment = commentsRepository.save(comment);
        return new CommentResponseEntity(
                savedComment.getCommentId(),
                savedComment.getText(),
                savedComment.getDatePosted().format(DATE_TIME_FORMATTER)
        );
    }
}
