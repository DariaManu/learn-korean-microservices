package com.ubb.blogservice.controller;

import com.ubb.blogservice.controller.response.CommentResponseEntity;
import com.ubb.blogservice.model.Comment;
import com.ubb.blogservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/post/{postId}/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final BlogService blogService;

    @GetMapping("/count")
    public Integer getNumberOfCommentsForPost(@PathVariable final Long postId) {
        return blogService.getNumberOfCommentForPost(postId);
    }

    @GetMapping("/{pageNumber}")
    public List<CommentResponseEntity> getNextComments(@PathVariable final Long postId, @PathVariable int pageNumber) {
        return blogService.getNextCommentsForPost(postId, pageNumber);
    }

    @PostMapping()
    public CommentResponseEntity addComment(@PathVariable final Long postId, @RequestBody final Comment comment) {
        return blogService.addComment(postId, comment);
    }
}
