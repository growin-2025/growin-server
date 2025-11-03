package ita.growin.domain.auth.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KakaoApiClient {

    private final RestTemplate restTemplate;
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public KakaoApiClient() {
        this.restTemplate = new RestTemplate();
    }

    public KakaoUserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserInfo> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    KakaoUserInfo.class
            );

            log.info("카카오 사용자 정보 조회 성공: {}", response.getBody());
            return response.getBody();

        } catch (Exception e) {
            log.error("카카오 API 호출 실패", e);
            throw new RuntimeException("카카오 사용자 정보 조회 실패", e);
        }
    }

    @Getter
    @Setter
    public static class KakaoUserInfo {
        private Long id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Getter
        @Setter
        public static class KakaoAccount {
            private String email;

            @JsonProperty("email_needs_agreement")
            private Boolean emailNeedsAgreement;

            private Profile profile;

            @Getter
            @Setter
            public static class Profile {
                private String nickname;
            }
        }
    }
}
