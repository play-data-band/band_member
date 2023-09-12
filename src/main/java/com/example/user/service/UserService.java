package com.example.user.service;

import com.example.user.common.RestError;
import com.example.user.common.RestResult;
import com.example.user.config.JwtService;
import com.example.user.domain.entity.Interest;
import com.example.user.domain.entity.User;
import com.example.user.domain.request.LoginRequest;
import com.example.user.domain.request.SignupRequest;
import com.example.user.domain.request.TeacherRequest;
import com.example.user.domain.response.LoginResponse;
import com.example.user.domain.response.UserResponse;
import com.example.user.repository.InterestRepository;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final JwtService jwtService;

    //중복이메일 검사 후 회원가입
    public ResponseEntity<RestResult<Object>> signupCheck(SignupRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.getEmail());

        if(byEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new RestResult<>("CONFLICT",new RestError("EMAIL_CONFLICT","이미 존재하는 이메일 입니다.")));
       }

        userRepository.save(request.toEntity());

        return ResponseEntity.ok(new RestResult<>("success","회원가입이 완료 되었습니다."));
    }

    public LoginResponse login(LoginRequest request){
        Optional<User> byEmailAndPassword = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        User user = byEmailAndPassword.orElseThrow(() -> new IllegalArgumentException("NOT FOUND"));
        String s = jwtService.makeToken(user);
        return new LoginResponse(s);
    }

    public UserResponse getMe(Long id){
        Optional<UserResponse> interestById = userRepository.findInterestById(id);
        return interestById.orElseThrow(IllegalArgumentException::new);

    }

    public ResponseEntity<RestResult<Object>> teacherLogin(TeacherRequest teacherRequest) {
        Optional<User> byEmail = userRepository.findByEmail(teacherRequest.getEmail());

        if(byEmail.isPresent()){
            return ResponseEntity.ok(new RestResult<>("CONFLICT","이미 존재하는 이메일 입니다."));
        }

        return ResponseEntity.ok(new RestResult<>("SUCCESS","존재하지 않는 회원 입니다."));
    }

    //회원가입
    private void signup(SignupRequest request){
        userRepository.save(request.toEntity());
    }


}
