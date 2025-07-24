package com.example.minitwitter.service;

import com.example.minitwitter.domain.Post;
import com.example.minitwitter.domain.Role;
import com.example.minitwitter.domain.User;
import com.example.minitwitter.exception.PostNotFoundException;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.repository.PostRepository;
import com.example.minitwitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LikeServiceTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    User user1, user2;
    Post post1, post2, post3, post4;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .username("user1")
                .dateOfBirth(LocalDate.of(1999, 9,12))
                .role(Role.USER)
                .password("password1")
                .email("user1@example.com")
                .dateOfJoin(LocalDateTime.of(2025, 9,12, 1, 2,3))
                .lastLogin(LocalDateTime.of(2025, 9,12, 1, 3,4))
                .build();
        user2 = User.builder()
                .username("user2")
                .dateOfBirth(LocalDate.of(200, 9,12))
                .role(Role.USER)
                .password("password2")
                .email("user2@example.com")
                .dateOfJoin(LocalDateTime.of(2025, 9,12, 1, 2,3))
                .lastLogin(LocalDateTime.of(2025, 9,12, 1, 3,4))
                .build();

        post1 = Post.builder()
           .user(user1)
           .text("text 1")
           .dateOfPublication(LocalDateTime.of(2022, 12, 01, 11,  11))
           .build();
        post2 = Post.builder()
           .user(user1)
           .text("text 2")
           .dateOfPublication(LocalDateTime.of(2022, 12, 01, 11,  12))
           .build();
        post3 = Post.builder()
           .user(user1)
           .text("text 3")
           .dateOfPublication(LocalDateTime.of(2022, 12, 01, 11,  13))
           .build();
        post4 = Post.builder()
                .user(user2)
                .text("text 4")
                .dateOfPublication(LocalDateTime.of(2022, 12, 01, 11,  14))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

    }

    @Test
    @Transactional
    void getAllLikesOnPost() throws PostNotFoundException, UserNotFoundException {
        likeService.toggleLikePost(post1.getId(), user1.getId());
    }

    @Test
    void getAllLikesByUser() {
    }

    @Test
    @Transactional
    void toggleLikePost() throws PostNotFoundException, UserNotFoundException {

        //When and then
        Long likeCount0 = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(0L, likeCount0);

        //When and then
        likeService.toggleLikePost(post1.getId(), user1.getId());
        Long likeCount1 = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(1L, likeCount1);

        //When and then
        likeService.toggleLikePost(post1.getId(), user1.getId());
        Long likeCount2 = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(0L, likeCount2);
        assertEquals(likeCount2, likeCount0);

        //When and then
        likeService.toggleLikePost(post1.getId(), user2.getId());
        Long likeCount3 = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(1L, likeCount3);

        //When and then
        likeService.toggleLikePost(post1.getId(), user1.getId());
        Long likeCount4 = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(2L, likeCount4);

        //When and then
        likeService.toggleLikePost(post1.getId(), user1.getId());
        Long likeCount5 = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(1L, likeCount5);

    }

    @Test
    void getLikeCountByUser() {
    }

    @Transactional
    @Test
    void getLikeCountOnPost() throws PostNotFoundException, UserNotFoundException {

        //When and then
        Long likeCountZero = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(0L, likeCountZero);

        //When and then
        likeService.toggleLikePost(post1.getId(), user1.getId());
        Long likeCountOne = likeService.getLikeCountOnPost(post1.getId());
        assertEquals(1L, likeCountOne);

    }

}