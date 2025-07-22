package com.example.minitwitter.controller;

import com.example.minitwitter.domain.UserChangeOfPasswordDto;
import com.example.minitwitter.domain.UserDto;
import com.example.minitwitter.domain.UserRegistrationDto;
import com.example.minitwitter.exception.UserNotFoundException;
import com.example.minitwitter.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping(value = "/{Userid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long Userid) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(Userid));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRegistrationDto));
    }

    @PutMapping(value = "/{Userid}/username")
    public ResponseEntity<Void> updateUserName(@PathVariable Long Userid, @RequestBody @Valid UserDto userDto) throws UserNotFoundException {
        userService.updateUserName(Userid, userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{Userid}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Long Userid, @RequestBody @Valid UserChangeOfPasswordDto userDto) throws UserNotFoundException {
        userService.updateUserPassword(Userid, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{Userid}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Userid) throws UserNotFoundException {
        userService.deleteUser(Userid);
        return ResponseEntity.noContent().build();
    }

}
