package potato.server.security.oauth;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import potato.server.user.domain.User;
import potato.server.user.spec.Gender;

import java.time.LocalDate;
import java.util.Map;

/**
 * 카카오에서 받아오는 사용자 정보 담는 DTO
 * @Author 정순원
 * @Since 2023-08-19
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class KakaoOAuth2UserAttribute extends OAuth2UserAttribute {

    private static final String KAKAO_PROVIDER_ID = "kakao";

    private String id;
    private Map<String, Object> kakaoAccount;


    @Override
    public User toEntity() {
       return User.builder()
                .providerName(KAKAO_PROVIDER_ID)
                .providerId(KAKAO_PROVIDER_ID+" "+getProviderId())//띄어쓰기 포함
                .email(getEmail())
                .name(getName())
                .gender(Gender.valueOf(getGender().toUpperCase())) //대소문자 구별하니 바꿔줘야 함
                .birth(LocalDate.parse(getBirthday()))
                .build();
    }

    //TODO
    @Override
    public String getProviderId() { return KAKAO_PROVIDER_ID + this.id;}

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getName() {
        return kakaoAccount.get("name").toString();
    }

    @Override
    public String getGender() {
        return kakaoAccount.get("gender").toString();
    }

    @Override
    public String getBirthday() {
        return kakaoAccount.get("birthyear").toString();
    }

    @Override
    public void setUserAttributesByOauthToken(String oauth2AccessToken) {


        JSONObject response = WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(oauth2AccessToken))
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();
        this.id = response.get("id").toString();
        this.kakaoAccount = (Map<String, Object>)response.get("kakao_account");
    }
}
