package com.example.friendfinder.config;

import com.example.friendfinder.dto.UserDto;
import com.example.friendfinder.helper.JwtStructure;
import com.example.friendfinder.mapper.UserMapper;
import com.example.friendfinder.model.Role;
import com.example.friendfinder.model.User;
import com.example.friendfinder.repo.UserRepo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtHandler {
    private final JwtStructure jwtToken;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final SecretKey key;
    private final JwtParser jwtParser;

    @Autowired
    public JwtHandler(JwtStructure jwtToken, UserRepo userRepo, UserMapper userMapper) {
        this.jwtToken = jwtToken;
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.key = Keys.hmacShaKeyFor(jwtToken.getSECRET_KEY().getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    public String generateJwtToken(User user) {
        Date now = new Date();
        Date expiryDate = Date.from(now.toInstant().plus(jwtToken.duration));
        return Jwts.builder().subject(user.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .claim("roles", user.getRoles().stream()
                        .map(Role::getName)
                        .toList())
                .signWith(key)
                .compact();


    }

    public UserDto validateJwtToken(String token) {
        try {
            Claims claims = jwtParser
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();

            User user = userRepo.findByUsername((username));
            if (user == null) {
                throw new RuntimeException("token.invalid");
            }

            return userMapper.toUserDto(user);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("token.expired");
        } catch (JwtException e) {
            throw new RuntimeException("token.invalid");
        }

    }



}
