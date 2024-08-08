package com.purna.controller;

import com.purna.dto.SearchResults;
import com.purna.dto.UserDTO;
import com.purna.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    Map<String,Object> response = new HashMap<>();

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public Mono<SearchResults> searchUser(@RequestParam String query){
        return searchService.search(query);
    }

    @GetMapping("/find")
    public Mono<ResponseEntity<Map<String, Object>>> searchUsers(@RequestParam String query) {
        Mono<List<UserDTO>> usersMono = searchService.searchUsers(query);
        return usersMono
                .map(users -> {
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("users", users);
                    return ResponseEntity.ok(responseBody);
                })
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/getByTitle")
    public Mono<ResponseEntity<Map<String, Object>>> getPostsByTitle(@RequestParam String query) {
        return searchService.searchPosts(query)
                .doOnNext(posts -> log.info("Controller received posts: {}", posts))
                .map(posts -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("posts", posts);
                    return ResponseEntity.ok(response);
                })
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/getPost")
    public Mono<ResponseEntity<Map<String, Object>>> getPost(@RequestParam String query) {
        return searchService.search(query)
                .map(searchResults -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("status", HttpStatus.OK.value());
                    response.put("message", "Fetching data is success");
                    response.put("result", searchResults);
                    return ResponseEntity.ok().body(response);
                })
                .onErrorResume(error -> {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("status", HttpStatus.NOT_FOUND.value());
                    errorResponse.put("message", "Fetching with given query is not found");
                    errorResponse.put("error", error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
                });
    }





}
