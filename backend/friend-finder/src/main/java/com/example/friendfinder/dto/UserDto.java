package com.example.friendfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String profileImagePath;
    private String bio;
    private Date CreatedAt;
    private Boolean Status;
    private List<Long> postsIds;
    private List<Long> rolesIds;


}
