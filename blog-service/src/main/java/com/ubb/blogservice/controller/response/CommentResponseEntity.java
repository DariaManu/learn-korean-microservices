package com.ubb.blogservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentResponseEntity {
    private Long commentId;
    private String text;
    private String datePosted;
}
