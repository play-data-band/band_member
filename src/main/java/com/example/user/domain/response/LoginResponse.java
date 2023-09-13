package com.example.user.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class LoginResponse {
    private String token;
    private String email;
    private String username;
    private String profileImgPath;
    private String mbti;
    private Long userId;
}
