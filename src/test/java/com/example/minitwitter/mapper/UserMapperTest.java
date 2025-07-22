package com.example.minitwitter.mapper;

import com.example.minitwitter.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testMapToUser_fromUserDto() {
        // Given
        UserDto userDto = UserDto.builder()
                .username("john")
                .email("john@example.com")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        // When
        User result = userMapper.mapToUser(userDto);

        // Then
        assertEquals("john", result.getUsername());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), result.getDateOfBirth());
        assertNotNull(result.getLastLogin());
    }

    @Test
    void testMapToUser_fromRegistrationDto() {
        // Given
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .userName("alice")
                .email("alice@example.com")
                .password("pass1234")
                .birthDate(LocalDate.of(1995, 5, 15))
                .build();

        // When
        User result = userMapper.mapToUser(dto);

        // Then
        assertEquals("alice", result.getUsername());
        assertEquals("alice@example.com", result.getEmail());
        assertEquals("pass1234", result.getPassword());
        assertEquals(LocalDate.of(1995, 5, 15), result.getDateOfBirth());
        assertNotNull(result.getDateOfJoin());
    }

    @Test
    void testMapToUser_fromLoginDto() {
        // Given
        UserLoginDto loginDto = UserLoginDto.builder()
                .email("bob@example.com")
                .password("secret")
                .build();

        // When
        User result = userMapper.mapToUser(loginDto);

        // Then
        assertEquals("bob@example.com", result.getEmail());
        assertEquals("secret", result.getPassword());
        assertNotNull(result.getLastLogin());
    }

    @Test
    void testMapToUserDto() {
        // Given
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .dateOfJoin(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        // When
        UserDto dto = userMapper.mapToUserDto(user);

        // Then
        assertEquals("testuser", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(user.getDateOfJoin(), dto.getRegistrationDate());
        assertEquals(user.getLastLogin(), dto.getLastLogin());
    }

    @Test
    void testMapToUserDtoList() {
        // Given
        User user = User.builder()
                .username("name")
                .email("email@example.com")
                .dateOfJoin(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        List<User> userList = List.of(user);

        // When
        List<UserDto> dtos = userMapper.mapToUserDtoList(userList);

        // Then
        assertEquals(1, dtos.size());
        assertEquals("name", dtos.get(0).getUsername());
    }
}