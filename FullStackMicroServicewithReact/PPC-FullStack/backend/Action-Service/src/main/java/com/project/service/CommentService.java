package com.project.service;

import com.project.entity.Comment;
import com.project.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Service
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private WebClient.Builder builder;

    public String saveComment(Comment comment){
        return commentRepository.saveComment(comment);
    }

    public String deleteComment(String viewComment) {
//        String findReviewIdURL = "http://localhost:9193/api/Comment/findByReviewId/" + reviewId;
//        Comment commentDetails = null;
//
//        try {
//            // Fetch the comment details from the external service
//            commentDetails = builder.build().get()
//                    .uri(findReviewIdURL)
//                    .retrieve()
//                    .bodyToMono(Comment.class)
//                    .block();
//
//            log.info("Fetched comment details: {}", commentDetails);
//
//        } catch (WebClientResponseException e) {
//            if (e.getCause() instanceof java.net.ConnectException) {
//                log.error("Error fetching CommentService: Service is offline", e);
//            } else {
//                log.error("Error fetching CommentService for reviewId {}: {}", reviewId, e.getMessage(), e);
//            }
//        } catch (Exception e) {
//            log.error("Unexpected error while fetching comment for reviewId {}: {}", reviewId, e.getMessage(), e);
//        }
//
//        if (commentDetails == null) {
//            throw new RuntimeException("Comment not found with reviewId: " + reviewId);
//        }
        return commentRepository.deleteComment(viewComment);
    }

}
