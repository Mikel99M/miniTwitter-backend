package com.example.minitwitter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogin;
    private LocalDate birthday;

    private List<PostDto> posts = new ArrayList<>();
    private List<LikeDto> likes = new ArrayList<>();
    private int numOfPosts;
    private int numOfLikesGiven;
}
