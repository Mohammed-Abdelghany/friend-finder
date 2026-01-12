package com.example.friendfinder.service;

import com.example.friendfinder.controller.vm.LoginReq;
import com.example.friendfinder.controller.vm.LoginRes;
import com.example.friendfinder.controller.vm.RegisterReq;
import com.example.friendfinder.controller.vm.RegisterRes;
import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
   ResponseEntity<?> login(LoginReq loginReq);
    UserDto loginWithEmail(String email, String password);

    ResponseEntity<RegisterRes> register(RegisterReq user);

}
