package com.example.minitwitter.service;

import com.example.minitwitter.domain.Post;
import com.example.minitwitter.domain.PostDto;
import com.example.minitwitter.domain.User;
import com.example.minitwitter.mapper.LikeMapper;
import com.example.minitwitter.mapper.PostMapper;
import com.example.minitwitter.mapper.UserMapper;
import com.example.minitwitter.repository.PostRepository;
import com.example.minitwitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final LikeMapper likeMapper;

    @Transactional
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::buildFullPostDto)
                .collect(Collectors.toList());
    }

    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        return buildFullPostDto(post);
    }

    public PostDto createPost(PostDto postDto) {
        User user = userRepository.findById(postDto.getUser().getId()).orElseThrow();
        Post post = postMapper.mapToPost(postDto, user);
        Post saved = postRepository.save(post);
        return buildFullPostDto(saved);
    }

    public PostDto editPostText(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setText(postDto.getText());
        return buildFullPostDto(postRepository.save(post));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    private PostDto buildFullPostDto(Post post) {
        PostDto dto = postMapper.mapToPostDto(post);
        dto.setUser(userMapper.mapToUserDto(post.getUser()));
        dto.setLikes(likeMapper.mapToLikeDtoList(post.getLikes()));
        return dto;
    }
}
