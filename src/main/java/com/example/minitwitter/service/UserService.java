package com.example.minitwitter.service;

import com.example.minitwitter.domain.*;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.mapper.LikeMapper;
import com.example.minitwitter.mapper.PostMapper;
import com.example.minitwitter.mapper.UserMapper;
import com.example.minitwitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final LikeMapper likeMapper;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::buildFullUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return buildFullUserDto(user);
    }

    public UserDto registerUser(final UserRegistrationDto userRegistrationDto) {
        User user = userMapper.mapToUser(userRegistrationDto);
        user.setRole(Role.USER);
        User saved = userRepository.save(user);
        return buildFullUserDto(saved);
    }

    public void updateUserName(final Long id, final UserDto userDto) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getUsername().equals(userDto.getUsername())) {
            user.setUsername(userDto.getUsername());
            userRepository.save(user);
        }
    }

    public void updateUserPassword(final Long id, final UserChangeOfPasswordDto dto) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals(dto.getNewPassword())
                && dto.getNewPassword().length() >= 8 && dto.getNewPassword().length() <= 20
                && dto.getNewPassword().equals(dto.getConfirmPassword())) {
            user.setPassword(dto.getNewPassword());
            userRepository.save(user);
        }
    }

    public void deleteUser(final Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    private UserDto buildFullUserDto(User user) {
        List<Post> posts = user.getPosts() != null ? user.getPosts() : new ArrayList<>();
        List<Like> likes = user.getLikedByUser() != null ? user.getLikedByUser() : new ArrayList<>();

        UserDto dto = userMapper.mapToUserDto(user);
        dto.setPosts(postMapper.mapToPostDtoList(posts));
        dto.setLikes(likeMapper.mapToLikeDtoList(likes));
        dto.setNumOfPosts(posts.size());
        dto.setNumOfLikesGiven(likes.size());
        return dto;
    }
}
