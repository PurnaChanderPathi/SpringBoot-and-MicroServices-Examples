package com.purna.service;

import com.purna.config.WebClientConfig;
import com.purna.dto.PostDTO;
import com.purna.dto.SearchResults;
import com.purna.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class SearchService {
    @Autowired
    private WebClient.Builder webClientBuilder;

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

    public Mono<List<PostDTO>> searchPosts(String query) {
        return postServiceClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/posts/getByTitle")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToFlux(PostDTO.class)
                .collectList()
                .doOnNext(posts -> log.info("Received posts: {}", posts))
                .doOnError(error -> log.error("Error occurred while fetching posts", error));
    }

    public Mono<SearchResults> search(String query){
        Mono<List<UserDTO>> users = searchUsers(query);
        Mono<List<PostDTO>> posts = searchPosts(query);

        return Mono.zip(users,posts,(userList,postList)-> new SearchResults(userList,postList));
    }
}
