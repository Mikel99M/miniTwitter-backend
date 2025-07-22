package com.example.minitwitter.controller;

import com.example.minitwitter.domain.PostDto;
import com.example.minitwitter.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/v1/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @GetMapping(value = "/{postid}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postid) {
        return ResponseEntity.ok().body(postService.getPostById(postid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postid) {
        return ResponseEntity.ok().body(postService.createPost(postid));
    }

    @PutMapping(value = "/{postid}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postid, @RequestBody PostDto postDto) {
        return ResponseEntity.ok().body(postService.editPostText(postid, postDto));
    }

    @DeleteMapping(value = "/{postid}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postid) {
        postService.deletePost(postid);
        return ResponseEntity.noContent().build();
    }

}
