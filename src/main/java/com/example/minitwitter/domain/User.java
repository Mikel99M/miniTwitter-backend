package com.example.minitwitter.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_USERNAME", nullable = false)
    private String username;

    @Column(name = "USER_ROLE", nullable = false)
    private Role role;

    @Column(name = "USER_PASSWORD", nullable = false)
    @Size(min = 8)
    private String password;

    @Column(name = "USER_DATE_OF_BIRTH", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    @Column(name = "USER_DATE_OF_JOIN", nullable = false)
    private LocalDateTime dateOfJoin;

    @Column(name = "USER_LAST_LOGIN", nullable = false)
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likedByUser = new ArrayList<>();


//    enum role {
//        ADMIN, USER
//    }
}
