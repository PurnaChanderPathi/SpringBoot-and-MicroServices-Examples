package com.purna.service;

import com.purna.dto.PostDTO;
import com.purna.dto.SearchResults;
import com.purna.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
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

    public Mono<List<PostDTO>> searchPosts(String query){
        return postServiceClient.get()
                .uri("/api/v1/posts/getByTitle?query={query}",query)
                .retrieve()
                .bodyToFlux(PostDTO.class)
                .collectList();
    }

    public Mono<SearchResults> search(String query){
        Mono<List<UserDTO>> users = searchUsers(query);
        Mono<List<PostDTO>> posts = searchPosts(query);

        return Mono.zip(users,posts,(userList,postList)-> new SearchResults(userList,postList));
    }
}
