package com.purna.controller;

import com.purna.model.Story;
import com.purna.service.StoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stories")
@Slf4j
public class StoryController {

    private final StoryService storyService;

    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Story> createStory(
            @RequestParam Long userId,
            @RequestParam String content,
            @RequestParam(value = "image", required = false)MultipartFile image) throws IOException {
        log.info("Received userId: " + userId);
        log.info("content",content);
        log.info("Received file: " + image.getOriginalFilename());

        Story story = new Story();
        story.setUserId(userId);
        story.setImage(image.getBytes());
        story.setContent(content);
        Story result = storyService.createStory(story);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body( result);

        }

        @GetMapping("/{id}")
        public ResponseEntity<byte[]> getStory(@PathVariable Long id){
        Story story = storyService.getStory(id);
        if(story != null){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return  new ResponseEntity<>(story.getImage(), headers, HttpStatus.OK);
        }else{
            return  ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Map<String,Object>> getStory(@PathVariable Long id){
//        Map<String,Object> result = storyService.getStory(id);
//        return ResponseEntity.ok().body(result);
//    }


//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Story>> getUserStories(@PathVariable Long userId){
//        List<Story> stories = storyService.getUserStories(userId);
//        return ResponseEntity.ok(stories);
//    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String,Object>> getUserStories(@PathVariable Long userId){
        Map<String,Object> result = storyService.getUserStories(userId);
        return ResponseEntity.ok().body(result);
    }

    }

