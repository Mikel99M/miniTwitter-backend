package com.example.minitwitter.mapper;

import com.example.minitwitter.domain.Post;
import com.example.minitwitter.domain.PostDto;
import com.example.minitwitter.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public Post mapToPost(final PostDto postDto, User user) {
        return Post.builder()
                .text(postDto.getText())
                .dateOfPublication(LocalDateTime.now())
                .user(user)
                .build();
    }

    public PostDto mapToPostDto(final Post post) {
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .dateOfPublication(post.getDateOfPublication())
                .build(); // user & likes added in service
    }

    public List<PostDto> mapToPostDtoList(final List<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            return Collections.emptyList();
        } else {
            return posts.stream()
                    .map(this::mapToPostDto)
                    .collect(Collectors.toList());
        }
    }
}
