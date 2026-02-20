package com.example.friendfinder.service;

import com.example.friendfinder.dto.PostDto;
import org.springframework.data.domain.Page;

public interface PostService {

    Page<PostDto> getPosts(int page , int size);
    PostDto createPost(String content, String mediaPath);
     void deletePost(Long postId);
     void updatePost(Long postId, String content, String mediaPath);



}
