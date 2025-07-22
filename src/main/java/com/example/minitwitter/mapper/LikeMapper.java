package com.example.minitwitter.mapper;

import com.example.minitwitter.domain.Like;
import com.example.minitwitter.domain.LikeDto;
import com.example.minitwitter.domain.Post;
import com.example.minitwitter.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LikeMapper {

    public Like mapToLike(User user, Post post) {
        return Like.builder()
                .user(user)
                .post(post)
                .build();
    }

    public LikeDto mapToLikeDto(Like like) {
        return LikeDto.builder()
                .userId(like.getUser().getId())
                .postId(like.getPost().getId())
                .build();
    }

    public List<LikeDto> mapToLikeDtoList(List<Like> likes) {
        if (likes == null || likes.isEmpty()) {
            return Collections.emptyList();
        } else {
            return likes.stream()
                    .map(this::mapToLikeDto)
                    .collect(Collectors.toList());
        }
    }
}
