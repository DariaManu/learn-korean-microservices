package com.ubb.blogservice.service;

import com.ubb.blogservice.controller.response.CommentResponseEntity;
import com.ubb.blogservice.controller.response.PostResponseEntity;
import com.ubb.blogservice.model.Comment;
import com.ubb.blogservice.model.Post;
import com.ubb.blogservice.repository.CommentsRepository;
import com.ubb.blogservice.repository.PostsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    @Mock
    private PostsRepository postsRepositoryMock;

    @Mock
    private CommentsRepository commentsRepositoryMock;

    @InjectMocks
    private BlogService blogService;

    private static List<Post> posts;
    private static List<Comment> comments;
    private static int PAGE_SIZE = 3;

    @BeforeAll
    public static void setUp() {
        posts = List.of(
                new Post(1L, "post1", LocalDateTime.of(2023, 6, 14, 4, 0, 0, 0)),
                new Post(2L, "post2", LocalDateTime.of(2023, 6, 14, 5, 0, 0, 0)),
                new Post(3L, "post3", LocalDateTime.of(2023, 6, 14, 6, 0, 0, 0)),
                new Post(4L, "post4", LocalDateTime.of(2023, 6, 14, 7, 0, 0, 0))
        );

        comments = List.of(
                new Comment(1L, "comment1", LocalDateTime.of(2023, 6, 14, 8, 0, 0, 0), posts.get(0)),
                new Comment(2L, "comment2", LocalDateTime.of(2023, 6, 14, 9, 0, 0, 0), posts.get(0)),
                new Comment(3L, "comment3", LocalDateTime.of(2023, 6, 14, 10, 0, 0, 0), posts.get(0)),
                new Comment(4L, "comment4", LocalDateTime.of(2023, 6, 14, 11, 0, 0, 0), posts.get(0))
        );
    }

    @Test
    public void givenPageNumberCorrectPostsAreReturned() {
        //given
        Mockito.when(postsRepositoryMock.findAll(PageRequest.of(1, PAGE_SIZE, Sort.by("datePosted").descending())))
                .thenReturn(new PageImpl<>(posts.subList(0, 3).stream().sorted(
                (p1, p2) -> p2.getDatePosted().compareTo(p1.getDatePosted())).toList()));
        Mockito.when(postsRepositoryMock.findAll(PageRequest.of(2, PAGE_SIZE, Sort.by("datePosted").descending())))
                .thenReturn(new PageImpl<>(List.of(posts.get(3))));

        //when
        List<PostResponseEntity> firstPagePosts = blogService.getNextPosts(1);
        List<PostResponseEntity> secondPagePosts = blogService.getNextPosts(2);
        //then

        assertEquals(firstPagePosts.size(), 3);
        assertEquals(firstPagePosts.get(0).getText(), "post3");
        assertEquals(firstPagePosts.get(1).getText(), "post2");
        assertEquals(firstPagePosts.get(2).getText(), "post1");

        assertEquals(secondPagePosts.size(), 1);
        assertEquals(secondPagePosts.get(0).getText(), "post4");
    }

    @Test
    public void givenPageNumberCorrectCommentsForPostAreReturned() {
        //given
        Mockito.when(postsRepositoryMock.findByPostId(1L)).thenReturn(posts.get(0));
        Mockito.when(commentsRepositoryMock.findNextByPost(posts.get(0), PageRequest.of(1, PAGE_SIZE, Sort.by("datePosted").descending())))
                .thenReturn(comments.subList(0, 3).stream().sorted(
                        (c1, c2) -> c2.getDatePosted().compareTo(c1.getDatePosted())).toList());
        Mockito.when(commentsRepositoryMock.findNextByPost(posts.get(0), PageRequest.of(2, PAGE_SIZE, Sort.by("datePosted").descending())))
                .thenReturn(List.of(comments.get(3)));

        //when
        List<CommentResponseEntity> firstPageComments = blogService.getNextCommentsForPost(1L, 1);
        List<CommentResponseEntity> secondPageComments = blogService.getNextCommentsForPost(1L, 2);

        //then
        assertEquals(firstPageComments.size(), 3);
        assertEquals(firstPageComments.get(0).getText(), "comment3");
        assertEquals(firstPageComments.get(1).getText(), "comment2");
        assertEquals(firstPageComments.get(2).getText(), "comment1");

        assertEquals(secondPageComments.size(), 1);
        assertEquals(secondPageComments.get(0).getText(), "comment4");
    }
}