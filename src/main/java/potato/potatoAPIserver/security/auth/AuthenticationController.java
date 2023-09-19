package potato.potatoAPIserver.security.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.security.auth.dto.AuthenticationResponse;
import potato.potatoAPIserver.security.auth.dto.RegisterRequest;
import potato.potatoAPIserver.security.oauth.dto.OAuth2AccessToken;

/**
 * 로그인, 회원가입, 토큰 재발급에 관한 API
 *
 * @Author 정순원
 * @Since 2023-08-07
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    //테스트용

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) OAuth2AccessToken oAuth2AccessToken) {
        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest, oAuth2AccessToken);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/login/{providerName}")
    public ResponseEntity<AuthenticationResponse> login(@PathVariable String providerName, @RequestHeader(HttpHeaders.AUTHORIZATION) OAuth2AccessToken oAuth2AccessToken) {
        AuthenticationResponse authenticationResponse = authenticationService.login(providerName, oAuth2AccessToken);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

//TODO
//    @PostMapping("/withdraw")
//    public void removeMember(@PathVariable String id) {
//        Optional<Member> member = memberRepository.findById(id);
//        memberRepository.deleteById(id);
//        authenticationService.deleteRedisToken(email);
//    }

    @PostMapping("/renew")
    public ResponseEntity<AuthenticationResponse> newTokenByRefreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        AuthenticationResponse authenticationResponse = authenticationService.newTokenByRefreshToken(refreshToken);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
