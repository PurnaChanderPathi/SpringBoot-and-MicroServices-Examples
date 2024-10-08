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

    @GetMapping("/find")
    public Mono<ResponseEntity<?>> searchUsers(@RequestParam String query) {
        return searchService.searchUsers(query)
                .map(userResponse -> {
                    UserDTO user = userResponse.getResult();
                    if (user != null) {
                        return ResponseEntity.ok(user);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(null);
                    }
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findPosts")
    public Mono<ResponseEntity<Map<String, Object>>> searchPost(@RequestParam String query) {
        return searchService.searchPosts(query)
                .map(posts -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("posts", posts);
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(e -> {
                    // Handle server offline error
                    if (e.getMessage().contains("Post server is offline")) {
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("message", "Post server is offline");
                        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse));
                    }
                    // Handle unexpected errors or other cases
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("message", "Post details with the given title not found");
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
                })
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Map<String, Object>> getPostById(@PathVariable Long id){
        Object result = searchService.searchById(id);
        if(result!=null){
            log.info("result: {}",result);
            response.put("status",HttpStatus.OK.value());
            response.put("message","Post details found with postId: "+id);
            response.put("result",result);
        }else{
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Post details not found with postId: "+id);
        }
    return ResponseEntity.ok(response);
    }

    @GetMapping("/searchByTitle")
    public Mono<ResponseEntity<?>> searchByTitle(@RequestParam String query) {
        return searchService.searchByTitle(query)
                .map(postResults -> {
                    if (postResults == null || postResults.isEmpty()) {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("No posts found for the query");
                    } else {
                        return ResponseEntity.ok(postResults);
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error fetching posts", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error Fetching Post"));
                });
    }


    @GetMapping("/PostsOrUsers")
    public Mono<ResponseEntity<Map<String, Object>>> search(@RequestParam String query) {
        Mono<ResponseEntity<?>> usersResponse = searchService.searchUsers(query)
                .map(userResponse -> {
                    UserDTO user = userResponse.getResult();
                    if (user != null) {
                        return ResponseEntity.ok(user);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                    }
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());

        Mono<ResponseEntity<Map<String, Object>>> postsResponse = searchService.searchPosts(query)
                .map(posts -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("posts", posts);
                    return ResponseEntity.ok(response);
                })
                .onErrorResume(e -> {
                    if (e.getMessage().contains("Post server is offline")) {
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("message", "Post server is offline");
                        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse));
                    }
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("message", "Post details with the given title not found");
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
                })
                .defaultIfEmpty(ResponseEntity.noContent().build());

        return Mono.zip(usersResponse, postsResponse)
                .map(tuple -> {
                    Map<String, Object> combinedResults = new HashMap<>();
                    combinedResults.put("users", tuple.getT1());
                    combinedResults.put("posts", tuple.getT2());
                    return ResponseEntity.ok(combinedResults);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
