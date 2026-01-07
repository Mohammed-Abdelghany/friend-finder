package com.example.friendfinder.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long id;
    private String Content;
    private String mediaPath;
    private Date CreatedAt;
    private Long authorId;
}
    
