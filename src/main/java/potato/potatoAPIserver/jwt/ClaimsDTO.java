package potato.potatoAPIserver.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import potato.potatoAPIserver.user.domain.User;

import static lombok.AccessLevel.PRIVATE;
/**
 * @author 정순원
 * @since 2023-08-022
 */
@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class ClaimsDTO {

    private final Long id;
    private final String providerId;
    private final String email;
    private final String role;


    /**
     * 로그인/회원가입 서비스에서 유저를 받아 ClaimsDTO로 바꿔주는 메소드
     */
    public static ClaimsDTO from(User user) {
        return ClaimsDTO.builder()
                .id(user.getId())
                .providerId(user.getProviderId())
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .build();
    }
}
