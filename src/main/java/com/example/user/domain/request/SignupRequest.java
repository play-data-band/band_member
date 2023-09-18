package com.example.user.domain.request;

import com.example.user.domain.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String mbti;
    private String imgPath;
    private String birthDay;

    public User toEntity(){
         return User
                .builder()
                .email(email)
                .password(password)
                .name(name)
                 .birthDay(birthDay)
                .mbti(mbti)
                .imgPath(imgPath)
                 .createdAt(LocalDateTime.now())
                .build();
    }
}
