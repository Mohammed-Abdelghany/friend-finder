package com.example.friendfinder.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Date;
import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String profileImagePath;
    private String profileCoverPath;
    private String bio;
    @Column(nullable = false)
    private Date CreatedAt;
    @Column(nullable = false)
    private Boolean Status;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
    @ManyToMany
    private List<Role> roles;

}
