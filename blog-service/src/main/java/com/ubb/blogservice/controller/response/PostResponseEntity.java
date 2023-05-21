package com.ubb.blogservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponseEntity {
    private Long postId;
    private String text;
    private String datePosted;
}
