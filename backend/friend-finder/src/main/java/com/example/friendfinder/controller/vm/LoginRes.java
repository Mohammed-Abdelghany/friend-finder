package com.example.friendfinder.controller.vm;

import com.example.friendfinder.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRes {
    private String token;
    private UserDto user;
    private String message;

}
