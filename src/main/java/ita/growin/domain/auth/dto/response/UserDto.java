package ita.growin.domain.auth.dto.response;

import ita.growin.domain.user.constant.*;
import ita.growin.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private String nickname;
    private LoginType type;
    private UserStatus status;
    private Work work;
    private InterestField interestField;
    private Target target;
    private LocalDateTime createdAt;
    private Boolean isNewUser;

    public static UserDto from(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .type(user.getType())
                .status(user.getStatus())
                .work(user.getWork())
                .interestField(user.getInterestField())
                .target(user.getTarget())
                .createdAt(user.getCreatedAt())
                .isNewUser(false)
                .build();
    }
}
