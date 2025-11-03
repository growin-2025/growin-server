package ita.growin.domain.auth.controller;

import ita.growin.domain.auth.dto.request.KakaoLoginRequest;
import ita.growin.domain.auth.dto.request.KakaoSignupRequest;
import ita.growin.domain.auth.dto.request.RefreshTokenRequest;
import ita.growin.domain.auth.dto.response.AuthResponse;
import ita.growin.domain.auth.service.AuthService;
import ita.growin.global.response.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/kakao/signup")
    public ResponseEntity<APIResponse<AuthResponse>> kakaoSignup(
            @Valid @RequestBody KakaoSignupRequest request
    ) {
        log.info("카카오 회원가입 요청");
        AuthResponse response = authService.kakaoSignup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.success(response));
    }

    @PostMapping("/kakao/login")
    public ResponseEntity<APIResponse<AuthResponse>> kakaoLogin(
            @Valid @RequestBody KakaoLoginRequest request
    ) {
        log.info("카카오 로그인 요청");
        AuthResponse response = authService.kakaoLogin(request);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        log.info("토큰 갱신 요청");
        AuthResponse response = authService.refreshToken(request);
        return ResponseEntity.ok(APIResponse.success(response));
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponse<Void>> logout(
            @RequestAttribute("userId") Long userId
    ) {
        log.info("로그아웃 요청 - User ID: {}", userId);
        authService.logout(userId);
        return ResponseEntity.ok(APIResponse.success(null));
    }
}

