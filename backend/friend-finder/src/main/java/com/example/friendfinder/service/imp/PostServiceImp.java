package com.example.friendfinder.service.imp;

import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.helper.AuthHelper;
import com.example.friendfinder.helper.Pagination;
import com.example.friendfinder.mapper.PostMapper;
import com.example.friendfinder.model.Post;
import com.example.friendfinder.model.User;
import com.example.friendfinder.repo.PostRepo;
import com.example.friendfinder.repo.UserRepo;
import com.example.friendfinder.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
  private final PostRepo postRepo;
  private final PostMapper postMapper;
  private final UserRepo userRepo;

    @Override
    public Page<PostDto> getPosts(int page, int size) {
       Page<Post>posts = postRepo.findPostByUserId(AuthHelper.getCurrentUser().getId(),Pagination.pageableMethod(page,size));
        return posts.map(
                postMapper::postToPostDto
        );
    }

    @Override
    public PostDto createPost(String content, String mediaPath) {

    UserDto user= AuthHelper.getCurrentUser();
    if(user == null) {
        throw new RuntimeException("User not found");
    }
    Optional<User> optionalUser = userRepo.findById(user.getId());

    if(optionalUser.isEmpty()) {
        throw new RuntimeException("User not found");

    }
        Date currentDate = new Date();
        Post post = new Post();
            post.setContent(content);
            post.setMediaPath(mediaPath);
            post.setUser( optionalUser.get());
            post.setCreatedAt(currentDate);
            Post savedPost = postRepo.save(post);
            PostDto postDto = postMapper.postToPostDto(savedPost);
            postDto.setAuthorId(user.getId());

            return postDto;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public void updatePost(Long postId, String content, String mediaPath) {

    }
}
