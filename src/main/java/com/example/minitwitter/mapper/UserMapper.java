package com.example.minitwitter.mapper;

import com.example.minitwitter.domain.User;
import com.example.minitwitter.domain.UserDto;
import com.example.minitwitter.domain.UserLoginDto;
import com.example.minitwitter.domain.UserRegistrationDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User mapToUser(final UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .lastLogin(LocalDateTime.now())
                .dateOfBirth(userDto.getBirthday())
                .build();
    }

    public User mapToUser(final UserRegistrationDto userRegistrationDto) {
        return User.builder()
                .username(userRegistrationDto.getUserName())
                .email(userRegistrationDto.getEmail())
                .password(userRegistrationDto.getPassword())
                .dateOfBirth(userRegistrationDto.getBirthDate())
                .dateOfJoin(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();
    }

    public User mapToUser(final UserLoginDto userLoginDto) {
        return User.builder()
                .email(userLoginDto.getEmail())
                .password(userLoginDto.getPassword())
                .lastLogin(LocalDateTime.now())
                .build();
    }

    public UserDto mapToUserDto(final User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .registrationDate(user.getDateOfJoin())
                .lastLogin(user.getLastLogin())
                .numOfPosts(Optional.ofNullable(user.getPosts()).map(List::size).orElse(0))
                .build();
    }

    public List<UserDto> mapToUserDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}