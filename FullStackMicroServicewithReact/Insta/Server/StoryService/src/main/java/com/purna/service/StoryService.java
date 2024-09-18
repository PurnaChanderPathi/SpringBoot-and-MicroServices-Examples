package com.purna.service;

import com.purna.model.Story;
import com.purna.repository.StoryRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StoryService {

    private final StoryRepository storyRepository;

    public StoryService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public Story createStory(Story story) throws IOException {
        Story storySave = new Story();
        story.setUserId(story.getUserId());
        story.setImage(story.getImage());
        story.setContent(story.getContent());
        story.setCreatedAt(LocalDateTime.now());
        story.setExpiresAt(LocalDateTime.now().plusHours(24));
        return storyRepository.save(story);
    }

    public Story getStory(Long id){
        return storyRepository.findById(id)
                .filter(story -> story.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(null);
    }

//    public Map<String,Object> getStory(Long id){
//        Map<String,Object> response = new HashMap<>();
//        Optional<Story> findById = storyRepository.findById(id);
//        if(findById.isPresent()){
//            byte[] image = storyRepository.findById(id)
//                    .filter(story -> story.getExpiresAt().isAfter(LocalDateTime.now())).orElse(null).getImage();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.IMAGE_JPEG);
//            response.put("status", HttpStatus.FOUND.value());
//            response.put("message","Story found with id :"+id);
//            response.put("Image",findById.get().getImage());
//        }else{
//            response.put("status",HttpStatus.NOT_FOUND.value());
//            response.put("message","Story not found with id :"+id);
//        }
//        return response;
//    }

//    public List<Story> getUserStories(Long userId){
//        return storyRepository.findAllByUserIdAndExpiresAtAfter(userId,LocalDateTime.now());
//    }

    public Map<String,Object> getUserStories(Long userId){
        Map<String,Object> response = new HashMap<>();
        List<Story> stories = storyRepository.findAllByUserIdAndExpiresAtAfter(userId,LocalDateTime.now());
        if(!stories.isEmpty()){
            response.put("status",HttpStatus.FOUND.value());
            response.put("message","Stories found with userId :"+userId);
            response.put("result",stories);
        }else{
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Stories not found with userId :"+userId);
        }
        return response;
    }
}
