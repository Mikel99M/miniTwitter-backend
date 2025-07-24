package com.example.minitwitter.service;

import com.example.minitwitter.domain.Like;
import com.example.minitwitter.domain.LikeDto;
import com.example.minitwitter.domain.Post;
import com.example.minitwitter.domain.User;
import com.example.minitwitter.exception.PostNotFoundException;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.mapper.LikeMapper;
import com.example.minitwitter.repository.LikeRepository;
import com.example.minitwitter.repository.PostRepository;
import com.example.minitwitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeMapper likeMapper;

    public List<LikeDto> getAllLikesOnPost(Long postId) throws PostNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));
        return likeMapper.mapToLikeDtoList(post.getLikes());
    }

    public List<LikeDto> getAllLikesByUser(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return likeMapper.mapToLikeDtoList(user.getLikedByUser());
    }

    public void toggleLikePost(Long postId, Long userId) throws PostNotFoundException, UserNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + postId));

        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(userId, postId);

        if (existingLike.isPresent()) {
            // Unlike
            likeRepository.delete(existingLike.get());
        } else {
            // Like
            Like like = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
        }
    }

    public long getLikeCountByUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId))
                .getLikedByUser().size();
    }

    public long getLikeCountOnPost(Long postId) {
        return likeRepository.countByPostId(postId);
    }


}
