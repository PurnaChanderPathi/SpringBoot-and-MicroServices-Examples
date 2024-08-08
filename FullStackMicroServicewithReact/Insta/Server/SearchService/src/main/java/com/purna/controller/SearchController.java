package com.purna.controller;

import com.purna.dto.PostDTO;
import com.purna.dto.SearchResults;
import com.purna.dto.UserDTO;
import com.purna.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
