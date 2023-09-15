package com.example.user.service;

import com.example.user.api.AlbumClient;
import com.example.user.api.BoardClient;
import com.example.user.api.ChattingClient;
import com.example.user.api.CommunityMemberClient;
import com.example.user.common.RestError;
import com.example.user.common.RestResult;
import com.example.user.config.JwtService;
import com.example.user.domain.entity.User;
import com.example.user.domain.request.*;
import com.example.user.domain.response.LoginResponse;
import com.example.user.domain.response.UserResponse;
import com.example.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CommunityMemberClient communityMemberClient;
    private final JwtService jwtService;
    private final AlbumClient albumClient;
    private final BoardClient boardClient;
    private final ChattingClient chattingClient;

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

    public ResponseEntity<RestResult<Object>> login(LoginRequest request){
        Optional<User> byEmailAndPassword = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());

        if (!byEmailAndPassword.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new RestResult<>("BAD_REQUEST",new RestError("BAD_REQUEST","존재하지 않는 정보입니다.")));
        }


        String s = jwtService.makeToken(byEmailAndPassword.get());

        LoginResponse loginResponse = LoginResponse.builder()
                .token(s)
                .email(byEmailAndPassword.get().getEmail())
                .username(byEmailAndPassword.get().getName())
                .mbti(byEmailAndPassword.get().getMbti())
                .profileImgPath(byEmailAndPassword.get().getImgPath())
                .userId(byEmailAndPassword.get().getId())
                .build();

        return ResponseEntity.ok(new RestResult<>("success",loginResponse));
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


    @Transactional
    public void updateUser(Long id, SignupRequest request){
        userRepository.updateUser(id, request);
        communityMemberClient.updateMemberInCommunityMember(new CommunityMemberRequest(id,null
                , request.getName(), request.getImgPath(), null,null

        ),id);
        albumClient.memberUpdateInAlbum(id,new AlbumUpdateRequest(
                request.getName(), request.getImgPath()
        ));
        boardClient.updateMemberBoard(id,new AlbumUpdateRequest(
                request.getName(), request.getImgPath()
        ));
        chattingClient.updateMember(id, new AlbumUpdateRequest(
                request.getName(), request.getImgPath()
        ));
    }

    //회원가입
    private void signup(SignupRequest request){
        userRepository.save(request.toEntity());
    }


}
