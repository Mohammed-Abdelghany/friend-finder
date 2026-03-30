package com.example.friendfinder.service.imp;

import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.helper.AuthHelper;
import com.example.friendfinder.helper.FileType;
import com.example.friendfinder.helper.Pagination;
import com.example.friendfinder.helper.UploadHelper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
  private final PostRepo postRepo;
  private final PostMapper postMapper;
  private final UserRepo userRepo;

    @Override
    public Page<PostDto> getPosts(int page, int size) {
       Page<Post>posts = postRepo.findByUserIdOrderByCreatedAtDesc(AuthHelper.getCurrentUser().getId(),Pagination.pageableMethod(page,size));
        return posts.map(
                postMapper::postToPostDto
        );
    }

    @Override
    public PostDto createPost(String content, MultipartFile file) {

        UserDto user = AuthHelper.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        User dbUser = userRepo.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, String> fileSave = new HashMap<>();

        if (file != null && !file.isEmpty()) {
            String type = file.getContentType();

            try {
                if (type != null && type.startsWith("image/")) {
                    fileSave = UploadHelper.uploadFile(file, FileType.IMAGE);

                } else if (type != null && type.startsWith("video/")) {
                    fileSave = UploadHelper.uploadFile(file, FileType.VIDEO);

                } else {
                    throw new RuntimeException("Unsupported file type");
                }

            } catch (IOException e) {
                throw new RuntimeException("File upload failed", e);
            }
        }

        Post post = new Post();
        post.setContent(content);
        post.setUser(dbUser);
        post.setCreatedAt(new Date());

        if (fileSave.get("url") != null) {
            post.setMediaPath(fileSave.get("url"));
        }

        Post savedPost = postRepo.save(post);

        PostDto postDto = postMapper.postToPostDto(savedPost);
        postDto.setAuthorId(user.getId());

        return postDto;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public void updatePost(Long postId, String content, MultipartFile file) {

    }

    @Override
    public Post getPostById(Long postId) {
        return postRepo.getPostById(postId);
    }
}
