package com.example.friendfinder.controller;

import com.example.friendfinder.controller.vm.CreatePostRequest;
import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.helper.UserAuthenticated;
import com.example.friendfinder.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public Page<PostDto> getPosts(@RequestParam int page, @RequestParam int size) {
        return postService.getPosts(page, size);
    }
    @PostMapping
    public PostDto createPost(@RequestBody CreatePostRequest request) {
        return postService.createPost(request.getContent(), request.getMediaPath());
    }

     public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

     public void updatePost(Long postId, String content, String mediaPath) {
        postService.updatePost(postId, content, mediaPath);
    }
}



