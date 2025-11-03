package ita.growin.domain.auth.dto.request;

import ita.growin.domain.user.constant.InterestField;
import ita.growin.domain.user.constant.Target;
import ita.growin.domain.user.constant.Work;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class KakaoSignupRequest {

    @NotBlank(message = "Access Token은 필수입니다.")
    private String accessToken;

    @NotNull(message = "직업 정보는 필수입니다.")
    private Work work;

    @NotNull(message = "관심분야는 필수입니다.")
    private InterestField interestField;

    @NotNull(message = "목표는 필수입니다.")
    private Target target;

    private String deviceToken;
}