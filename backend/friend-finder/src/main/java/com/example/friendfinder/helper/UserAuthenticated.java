package com.example.friendfinder.helper;

import com.example.friendfinder.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.swing.text.html.Option;
import java.util.Optional;

public class UserAuthenticated {
    public static UserDto getUserDtoAuthenticated() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .filter(UserDto.class::isInstance)
                .map(UserDto.class::cast)
                .orElseThrow(() -> new RuntimeException("user.notfound"));
    }
}
