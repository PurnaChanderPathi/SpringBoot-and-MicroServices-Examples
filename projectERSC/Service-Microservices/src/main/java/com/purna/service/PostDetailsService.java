package com.purna.service;

import com.purna.entity.PostDetails;
import com.purna.repositories.PostDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostDetailsService {

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    public PostDetails savePost(PostDetails postDetails){
        PostDetails savePost = new PostDetails();
        savePost.setTitle(postDetails.getTitle());
        savePost.setContent(postDetails.getContent());
        return postDetailsRepository.save(savePost);
    }


}
