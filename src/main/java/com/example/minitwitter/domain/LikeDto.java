package com.example.minitwitter.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {

    @NotBlank(message = "UserId is required")
    private Long userId;

    @NotBlank(message = "PostId is required")
    private Long postId;
}
