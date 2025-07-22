package com.example.minitwitter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangeOfPasswordDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
