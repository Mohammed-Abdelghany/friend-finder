package com.example.friendfinder.controller;

import com.example.friendfinder.controller.vm.CreatePostRequest;
import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.helper.UserAuthenticated;
import com.example.friendfinder.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController

@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public Page<PostDto> getPosts(@RequestParam(defaultValue  ="1") int page, @RequestParam(defaultValue  ="10") int size) {

        return postService.getPosts(page, size);
    }
    @PostMapping
    public PostDto createPost(@RequestPart("data") CreatePostRequest request,
                              @RequestPart(value = "file",required = false) MultipartFile file) {

        return postService.createPost(request.getContent(), file);
    }

     public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

     public void updatePost(Long postId, String content, MultipartFile file) {
        postService.updatePost(postId, content, file);
    }
}



