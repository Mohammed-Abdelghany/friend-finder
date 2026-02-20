package com.example.friendfinder.service.imp;

import com.example.friendfinder.config.JwtHandler;
import com.example.friendfinder.controller.vm.*;
import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.mapper.UserMapper;
import com.example.friendfinder.model.Role;
import com.example.friendfinder.model.User;
import com.example.friendfinder.repo.RoleRepo;
import com.example.friendfinder.repo.UserRepo;
import com.example.friendfinder.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImp implements AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtHandler jwtHandler;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    @Autowired
    public AuthServiceImp(UserRepo userRepo, JwtHandler jwtHandler, PasswordEncoder passwordEncoder, UserMapper userMapper, RoleRepo roleRepo) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtHandler = jwtHandler;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }
    @Override
    @Transactional
    public ResponseEntity<?> login(LoginReq loginReq) {
        User user = userRepo.findByUsername(loginReq.getUsername());
        if (user != null && passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            String token = jwtHandler.generateJwtToken(user);
            UserDto userDto = userMapper.toUserDto(user);
            LoginRes loginRes = new LoginRes(token,userDto,"login is success");
            return ResponseEntity.ok(loginRes);
        }
        return ResponseEntity.status(401).body(new ErrorResponse("Invalid username or password",401));
    }

    @Override
    public UserDto loginWithEmail(String email, String password) {
        return null;
    }
    @Override
    @Transactional
    public ResponseEntity<RegisterRes> register(RegisterReq registerReq) {

        if (userRepo.existsByUsername(registerReq.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RegisterRes("Username already exists",null));
        }

        if (userRepo.existsByEmail(registerReq.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RegisterRes("Email already exists",null));
        }

        Role userRole = roleRepo.findByName("USER").orElseGet(() -> {
                    Role role = new Role();
                    role.setName("USER");
                    return roleRepo.save(role);
                });

        User newUser = new User();
        newUser.setUsername(registerReq.getUsername());
        newUser.setEmail(registerReq.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        newUser.setBio(registerReq.getBio());
//        newUser.setProfileImagePath(registerReq.getProfileImagePath());
//        newUser.setProfileCoverPath(registerReq.getProfileCoverPath());
        newUser.setRoles(List.of(userRole));
        newUser.setStatus(true);
        newUser.setCreatedAt(new Date());
       User saverdUser= userRepo.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterRes("User registered successfully",saverdUser.getId()));

    }


}
