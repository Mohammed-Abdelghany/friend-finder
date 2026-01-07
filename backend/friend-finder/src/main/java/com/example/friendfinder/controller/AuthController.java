package com.example.friendfinder.controller;

import com.example.friendfinder.controller.vm.LoginReq;
import com.example.friendfinder.controller.vm.LoginRes;
import com.example.friendfinder.controller.vm.RegisterReq;
import com.example.friendfinder.controller.vm.RegisterRes;
import com.example.friendfinder.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping
    public ResponseEntity<LoginRes> login(@Valid @RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterRes> register(@Valid @RequestBody RegisterReq registerReq) {
        return authService.register(registerReq);

    }


}
