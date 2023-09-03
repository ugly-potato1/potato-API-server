package potato.potatoAPIserver.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String token = parseBearerToken(request);

      // Validation Access Token
      if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
          Authentication authentication = tokenProvider.getAuthentication(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          log.debug(authentication.getName() + "의 인증정보 저장");
      } else {
          log.debug("유효한 JWT 토큰이 없습니다.");
      }

      filterChain.doFilter(request, response);
  }

  private String parseBearerToken(HttpServletRequest request) {
      String bearerToken = request.getHeader("Authorization");

      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
          return bearerToken.substring(7);
      }
      return null;
  }
}