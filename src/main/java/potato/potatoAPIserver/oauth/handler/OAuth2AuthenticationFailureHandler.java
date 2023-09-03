package potato.potatoAPIserver.oauth.handler;

import static potato.potatoAPIserver.jwt.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import potato.potatoAPIserver.global.util.CookieUtil;
import potato.potatoAPIserver.jwt.CookieAuthorizationRequestRepository;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private final CookieAuthorizationRequestRepository authorizationRequestRepository;

	  @Override
	  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,AuthenticationException exception) throws IOException {
	    String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
	        .map(Cookie::getValue)
	        .orElse("/");

	    targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
	        .queryParam("error", exception.getLocalizedMessage())
	        .build().toUriString();

	    authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	    getRedirectStrategy().sendRedirect(request, response, targetUrl);
	  }
}
