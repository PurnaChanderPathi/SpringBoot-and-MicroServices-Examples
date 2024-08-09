package com.purna.service;

import com.purna.model.Story;
import com.purna.repository.StoryRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<Story> getUserStories(Long userId){
        return storyRepository.findAllByUserIdAndExpiresAtAfter(userId,LocalDateTime.now());
    }
}
