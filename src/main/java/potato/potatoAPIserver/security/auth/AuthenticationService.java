package potato.potatoAPIserver.security.auth;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
import potato.potatoAPIserver.redis.RedisUtil;
import potato.potatoAPIserver.security.auth.dto.AuthenticationResponse;
import potato.potatoAPIserver.security.auth.dto.ClaimsDTO;
import potato.potatoAPIserver.security.auth.dto.RegisterRequest;
import potato.potatoAPIserver.security.jwt.JwtService;
import potato.potatoAPIserver.security.oauth.AuthorizionRequestHeader;
import potato.potatoAPIserver.security.oauth.OAuth2UserAttribute;
import potato.potatoAPIserver.security.oauth.OAuth2UserAttributeFactory;
import potato.potatoAPIserver.user.domain.Policy;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.repository.PolicyRepository;
import potato.potatoAPIserver.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final RedisUtil redisUtil;
    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    /**
     * 회원가입
     * member 저장, 수신동의 저장, 엑세스,리프레쉬토큰 생성, redis에 리프레쉬 토큰 저장
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest registerRequest, AuthorizionRequestHeader authorizionRequestHeader) {
        //카카오인지 네이버인지 선택
        OAuth2UserAttribute oAuth2UserAttribute = OAuth2UserAttributeFactory.of(registerRequest.getProviderName());
        String oauth2AccessToekn = authorizionRequestHeader.getAccessToken().replace("Bearer ", "");
        //정보 추출
        oAuth2UserAttribute.setUserAttributesByOauthToken(oauth2AccessToekn);
        checkRegistration(oAuth2UserAttribute.getProviderId());
        User user = saveInformation(registerRequest, oAuth2UserAttribute);
        return generateToken(user);
    }

    /**
     * 유저 리프레쉬 토큰의 만료기간까지 다 지났을 때 로그인
     * (리프레쉬 토큰이 살아있을 때 로그인은 newTokenByRefreshToken()를 호출한다)
     * 엑세스,리프레쉬토큰 생성, redis에 리프레쉬 토큰 저장
     */
    @Transactional
    public AuthenticationResponse login(String providerName, AuthorizionRequestHeader authorizionRequestHeader) {
        OAuth2UserAttribute oAuth2UserAttribute = OAuth2UserAttributeFactory.of(providerName);
        String oauth2AccessToekn = authorizionRequestHeader.getAccessToken().replace("Bearer ", "");
        //정보 추출
        oAuth2UserAttribute.setUserAttributesByOauthToken(oauth2AccessToekn);
        String providerId = oAuth2UserAttribute.getProviderId();
        User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_JOINED));
        return generateToken(user);
    }

    /**
     * 리프레쉬 토큰 재발급
     */
    @Transactional
    public AuthenticationResponse newTokenByRefreshToken(String refreshToken) {
        String providerId = jwtService.parseProviderId(refreshToken);
        User user = userRepository.findByProviderId(providerId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));
        jwtService.isTokenValid(refreshToken);
        if (redisUtil.findByKey(providerId).toString().equals(refreshToken)) {
            return generateToken(user);
        }
        throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.REFRESHTOKEN_OUTDATED);
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
                .accessTokenExpiration(jwtService.parseExpiration(accessToken))
                .refreshTokenExpiration(jwtService.parseExpiration(refreshToken))
                .build();
    }


    private void checkRegistration(String providerId) {
        if (userRepository.existsByProviderId(providerId))
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.USER_ALREADY_JOIN);
    }

    private User saveInformation(RegisterRequest registerRequest, OAuth2UserAttribute oAuth2UserAttribute) {
        User user = oAuth2UserAttribute.toEntity();
        Policy policy = Policy.builder().
                user(user).
                registerRequest(registerRequest).
                build();
        userRepository.save(user);
        policyRepository.save(policy);
        return user;
    }
}
