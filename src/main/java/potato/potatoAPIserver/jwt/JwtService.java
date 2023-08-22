package potato.potatoAPIserver.jwt;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import potato.potatoAPIserver.user.repository.UserRepository;

import java.security.Key;
import java.util.*;

/**
 * 엑세스, 리프레쉬 토큰 생성, 클레임 추출, 토큰 검증
 *
 * @Author 정순원
 * @Since 2023-08-14
 */
@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secretKey}")
    private String secretKey;
    @Value("${jwt.accessTokenExpiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshTokenExpiration;

    private final UserRepository userRepository;
    private static final String HEADER_PREFIX = "Bearer ";

    /**
     *  User를 그대로 넘겨줘서 claim을 추출해도 되지만, user에 있는 모든 속성을 쓰는 것이 아니기 때문에 따로 ClaimsDTO를 만들었습니다.
     *  즉, ClaimsDTO는 엑세스,리프레쉬 토큰에 담길 id, providerId, email, role이 담겼습니다
     */
    //엑세스토큰 생성
    public String generateAccessToken(ClaimsDTO claimsDTO) {
        Claims claims = claimsByClaimsDTO(claimsDTO);
        return buildToken(claims, accessTokenExpiration);
    }

    //리프레쉬토큰 생성
    public String generateRefreshToken(ClaimsDTO claimsDTO) {
        Claims claims = claimsByClaimsDTO(claimsDTO);
        return buildToken(claims, refreshTokenExpiration);
    }

    private Claims claimsByClaimsDTO(ClaimsDTO claimsDTO) {
        Claims claims = Jwts.claims();
        claims.setSubject(claimsDTO.getProviderId());
        claims.put("id", claimsDTO.getId());
        claims.put("email", claimsDTO.getEmail());
        claims.put("role", claimsDTO.getRole());
        return claims;
    }

    private String buildToken(Claims claims, Long expiration) {
        return Jwts.
                builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 시크릿키를 디코드해서 쓰는 사람도 있고 인코드 해서 쓰는 사람도 있고
     * 차이점은 공부해보고 나중에 수정하겠습니다
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰에서 클레임추출 메소드들
    public String parseProviderId(String token) { return (String) parseAllClaims(token).get("providerid"); }

    public Date parseExpiration(String token) {
        return parseAllClaims(token).getExpiration();
    }

    public Claims parseAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //토큰 검증 메소드
    //TODO
    public boolean isTokenValid(String token) {
        String providerId = parseProviderId(token);
        Date expiration = parseExpiration(token);
        if ( userRepository.existsByProviderId(providerId) && expiration.before(new Date()))
            return true;
        return false;
    }

    //request 헤더에서 토큰 추출
    public String parseTokenFrom(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(HEADER_PREFIX))
            return authorizationHeader.replace(HEADER_PREFIX, "");
        return null;
    }
//      엑세스토큰에 담겨있는 사용자에게 권한 주는 메소드 (필요하면 쓰세용)
//    public Authentication getAuthentication(String accessToken) {
//
//        Claims claims = parseAllClaims(accessToken);
//        String email = claims.getSubject();
//        Long id = (Long) (claims.get("id"));
//        String providerId = claims.get("providerId").toString();
//        String authority =  claims.get("authority").toString();
//        AuthMemberDTO authMemberDTO = AuthMemberDTO.builder()
//                .id(id)
//                .providerId(providerId)
//                .email(email)
//                .authority(authority)
//                .build();
//        Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(authority));
//        return new UsernamePasswordAuthenticationToken(authMemberDTO, "", authorities);
//    }
}
