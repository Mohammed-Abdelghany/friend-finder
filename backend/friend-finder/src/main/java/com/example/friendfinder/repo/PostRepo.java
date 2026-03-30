package com.example.friendfinder.repo;

import com.example.friendfinder.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findPostByUserId(Long userId,Pageable pageable);

   Post getPostById(Long id);


    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
