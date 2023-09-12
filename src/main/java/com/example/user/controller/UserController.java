package com.example.user.controller;

import com.example.user.config.JwtService;
import com.example.user.config.TokenInfo;
import com.example.user.domain.entity.User;
import com.example.user.domain.request.LoginRequest;
import com.example.user.domain.request.SignupRequest;
import com.example.user.domain.response.LoginResponse;
import com.example.user.domain.response.UserResponse;
import com.example.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.el.parser.Token;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(originPatterns = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody SignupRequest request) {
        userService.signupCheck(request);
        return "회원가입 성공";
    }

    //로그인
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request){
        return userService.login(request);
    }

    @GetMapping("/me")
    public UserResponse getMe(@RequestHeader(name = "Authorization") String token1){
        String token=token1.replace("Bearer ", "");
        System.out.println(token);
        TokenInfo tokenInfo = jwtService.parseToken(token);
        System.out.println(tokenInfo.getId());
        return userService.getMe(tokenInfo.getId());
    }
}
