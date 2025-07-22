package com.example.minitwitter.controller;

import com.example.minitwitter.domain.LikeDto;
import com.example.minitwitter.exception.PostNotFoundException;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/v1/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LikeDto>> getAllLikesByUser(@RequestParam Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(likeService.getAllLikesByUser(userId));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getAllLikesOnPost(@RequestParam Long postId) throws PostNotFoundException {
        return ResponseEntity.ok(likeService.getAllLikesOnPost(postId));
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getLikeCountByUser(@RequestParam Long userId) throws UserNotFoundException {
        return ResponseEntity.ok(likeService.getLikeCountByUser(userId));
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getLikeCountOnPost(@RequestParam Long postId) throws PostNotFoundException {
        return ResponseEntity.ok(likeService.getLikeCountOnPost(postId));
    }

    @PostMapping("/toggle")
    public ResponseEntity<Void> toggleLike(@RequestParam Long postId, @RequestParam Long userId
    ) throws PostNotFoundException, UserNotFoundException {
        likeService.toggleLikePost(postId, userId);
        return ResponseEntity.ok().build();
    }

}
