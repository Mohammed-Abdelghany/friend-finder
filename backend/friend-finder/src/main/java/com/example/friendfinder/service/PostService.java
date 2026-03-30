package com.example.friendfinder.service;

import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    Page<PostDto> getPosts(int page , int size);
    PostDto createPost(String content, MultipartFile file);
     void deletePost(Long postId);
     void updatePost(Long postId, String content, MultipartFile file);
     Post getPostById(Long postId);



}
