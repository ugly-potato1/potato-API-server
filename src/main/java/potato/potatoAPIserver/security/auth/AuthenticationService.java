package potato.potatoAPIserver.security.auth;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import potato.potatoAPIserver.redis.RedisUtil;
import potato.potatoAPIserver.security.auth.dto.AuthenticationResponse;
import potato.potatoAPIserver.security.auth.dto.ClaimsDTO;
import potato.potatoAPIserver.security.auth.dto.RegisterRequest;
import potato.potatoAPIserver.security.auth.jwt.JwtService;
import potato.potatoAPIserver.security.oauth.dto.OAuth2AccessToken;
import potato.potatoAPIserver.security.oauth.dto.OAuth2UserAttribute;
import potato.potatoAPIserver.security.oauth.dto.OAuth2UserAttributeFactory;
import potato.potatoAPIserver.user.domain.Policy;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.repository.PolicyRepository;
import potato.potatoAPIserver.user.repository.UserRepository;

/**
 * 로그인, 회원가입, 토큰 재발급에 관한 서비스
 *
 * @Author 정순원
 * @Since 2023-08-19
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final RedisUtil redisUtil;

    /**
     * 회원가입
     * member 저장, 수신동의 저장, 엑세스,리프레쉬토큰 생성, redis에 리프레쉬 토큰 저장
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest, OAuth2AccessToken oAuth2AccessToken) {
        //카카오인지 네이버인지 선택
        OAuth2UserAttribute oAuth2UserAttribute = OAuth2UserAttributeFactory.of(registerRequest.getProviderName());
        //정보 추출
        oAuth2UserAttribute.UserAttributesByOAuthToken(oAuth2AccessToken);
        User user = oAuth2UserAttribute.toEntity();
        Policy policy = Policy.builder()
                .user(user)
                .registerRequest(registerRequest)
                .build();
        userRepository.save(user);
        policyRepository.save(policy);
        return generateToken(user);
    }

    /**
     * 유저 리프레쉬 토큰의 만료기간까지 다 지났을 때 로그인
     * (리프레쉬 토큰이 살아있을 때 로그인은 newTokenByRefreshToken()를 호출한다)
     * 엑세스,리프레쉬토큰 생성, redis에 리프레쉬 토큰 저장
     */
    @Transactional
    public AuthenticationResponse login(String providerName, OAuth2AccessToken oAuth2AccessToken) {
        OAuth2UserAttribute oAuth2UserAttribute = OAuth2UserAttributeFactory.of(providerName);
        oAuth2UserAttribute.UserAttributesByOAuthToken(oAuth2AccessToken);
        String providerId = oAuth2UserAttribute.getProviderId();
        User user = userRepository.findByProviderId(providerId).orElseThrow();
        return generateToken(user);
    }

    /**
     * 리프레쉬 토큰 재발급
     */
    @Transactional
    public AuthenticationResponse newTokenByRefreshToken(String refreshToken) {
        String providerId = jwtService.parseProviderId(refreshToken);
        User user = userRepository.findByEmail(providerId).orElseThrow();
        if (jwtService.isTokenValid(refreshToken)) {
            return generateToken(user);
        }
        return null;
    }

    /**
     * 엑세스 토큰 리프레쉬 토큰 생성, 레디쉬에 리프레쉬 토큰 저장
     */
    public AuthenticationResponse generateToken(User user) {
        ClaimsDTO claimsDTO = ClaimsDTO.from(user);
        String providerId = claimsDTO.getProviderId();
        String accessToken = jwtService.generateAccessToken(claimsDTO);
        String refreshToken = jwtService.generateRefreshToken(claimsDTO);
        redisUtil.save(providerId, refreshToken, refreshTokenExpiration);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
