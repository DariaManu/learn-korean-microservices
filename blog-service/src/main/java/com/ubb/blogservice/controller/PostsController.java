package com.ubb.blogservice.controller;

import com.ubb.blogservice.controller.response.PostResponseEntity;
import com.ubb.blogservice.model.Post;
import com.ubb.blogservice.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {
    private final BlogService blogService;

    @GetMapping("/count")
    public Integer getNumberOfPosts() {
        return blogService.getNumberOfPosts();
    }

    @GetMapping("/{pageNumber}")
    public List<PostResponseEntity> getNextPosts(@PathVariable final int pageNumber) {
        return blogService.getNextPosts(pageNumber);
    }

    @PostMapping()
    public PostResponseEntity addPost(@RequestBody final Post post) {
        return blogService.addPost(post);
    }
}
