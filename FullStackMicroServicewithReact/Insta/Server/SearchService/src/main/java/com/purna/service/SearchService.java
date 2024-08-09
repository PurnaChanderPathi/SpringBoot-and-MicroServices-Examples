package com.purna.service;

import com.purna.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SearchService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    Map<String,Object> response = new HashMap<>();

    private final WebClient userServiceClient;
    private final WebClient postServiceClient;

    public SearchService(WebClient.Builder webClientBuilder) {
        this.userServiceClient = webClientBuilder.baseUrl("http://localhost:9195").build();
        this.postServiceClient = webClientBuilder.baseUrl("http://localhost:9196").build();
    }

    public Mono<List<UserDTO>> searchUsers(String query){
        return userServiceClient.get()
                .uri("/api/v1/users/findByUsername?query={query}",query)
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList();
    }

    public Mono<List<Object>> searchPosts(String query) {
        return postServiceClient.get()
                .uri("/api/v1/posts/getByTitle?query={query}", query)
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .onErrorResume(WebClientRequestException.class, e -> {
                    if (e.getCause() instanceof java.net.ConnectException) {
                        // Handle server offline scenario
                        return Mono.error(new RuntimeException("Post server is offline"));
                    } else {
                        // Handle not found scenario
                        return Mono.empty();
                    }
                });
    }

    public Mono<List<Object>> searchByTitle(String query) {
        //String postUrl = "http://localhost:9196/api/v1/posts/getByTitle/" + query;

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(9196)
                        .path("/api/v1/posts/getByTitle")
                        .queryParam("query",query)
                        .build())
                .retrieve()
                .bodyToFlux(Object.class)
                .collectList()
                .onErrorResume(WebClientRequestException.class, e -> {
                    if (e.getCause() instanceof java.net.ConnectException) {
                        log.error("Error Fetching Post", e);
                        return Mono.error(new RuntimeException("Post service is offline"));
                    } else {
                        log.error("Error Fetching Post with postTitle {}: {}", query, e);
                        return Mono.error(new RuntimeException("Error Fetching Post"));
                    }
                });
    }


    public Object searchById(Long id){
            String postUrl = "http://localhost:9196/api/v1/posts/"+id;
            Object postResult = null;
            try {
                postResult = webClientBuilder.build().get()
                        .uri(postUrl)
                        .retrieve()
                        .bodyToMono(Object.class)
                        .block();
            }catch (WebClientRequestException e){
                if(e.getCause() instanceof java.net.ConnectException){
                    log.error("Error Fetching Post", e);
                    response.put("message", "Post service is offline");
                    response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
                }else{
                    log.error("Error Fetching Post with postId {}: {}", id, e);
                    response.put("message", "Error Fetching  Post");
                    response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
            }
            return postResult;
    }

}
