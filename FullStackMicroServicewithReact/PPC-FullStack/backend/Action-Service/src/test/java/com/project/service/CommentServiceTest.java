package com.project.service;

import com.project.entity.Comment;
import com.project.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    void AddComment(){
        Comment comment = new Comment();
        comment.setReviewId("PPPS");
        comment.setViewComment("Pathi");

        String expectedResult = comment.toString();


        Mockito.when(commentRepository.saveComment(comment)).thenReturn(String.valueOf(comment));

        String result = commentService.saveComment(comment);

        Assertions.assertEquals(expectedResult,result);
    }

}