package com.example.friendfinder;

import com.example.friendfinder.dto.PostDto;
import com.example.friendfinder.model.Post;
import com.example.friendfinder.repo.PostRepo;
import com.example.friendfinder.repo.UserRepo;
import com.example.friendfinder.service.PostService;
import com.example.friendfinder.service.imp.PostServiceImp;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class FriendFinderApplicationTests {

@Mock
private PostRepo postRepo;
@InjectMocks
private PostServiceImp postService;
    @Test
    public void getPostTest() {
        Mockito.when(postRepo.getPostById(1L))
                .thenReturn(new Post(1L, "Test content", "Test media path", null, null));
        Post post = postService.getPostById(1L);
        Assertions.assertEquals(1L, post.getId());
        Mockito.verify(postRepo, Mockito.times(1)).getPostById(1L); // ✅ 1L مش 2L
    }



}
