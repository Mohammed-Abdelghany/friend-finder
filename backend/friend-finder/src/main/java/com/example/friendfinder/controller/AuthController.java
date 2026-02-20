package com.example.friendfinder.controller;

import com.example.friendfinder.controller.vm.*;
import com.example.friendfinder.model.User;
import com.example.friendfinder.repo.UserRepo;
import com.example.friendfinder.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserRepo userRepo;

    @Autowired
    public AuthController(AuthService authService, UserRepo userRepo) {
        this.authService = authService;
        this.userRepo = userRepo;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterRes> register(@Valid @RequestBody RegisterReq registerReq
    ) {
        return authService.register(registerReq);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (updates.containsKey("profileImagePath")) {
            user.setProfileImagePath(updates.get("profileImagePath"));
        }
        if (updates.containsKey("profileCoverPath")) {
            user.setProfileCoverPath(updates.get("profileCoverPath"));
        }
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }




}
