package potato.potatoAPIserver.global.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import potato.potatoAPIserver.oauth.service.CustomOAuth2UserService;
import potato.potatoAPIserver.jwt.JwtAuthenticationFilter;
import potato.potatoAPIserver.oauth.handler.OAuth2AuthenticationFailureHandler;
import potato.potatoAPIserver.oauth.handler.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {
	
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    
    @Value("${app.origin.url}")
    private String originUrl;
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L);
                return config;
            }
		}
				))
			.httpBasic(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement((sessionManagement) ->
            	sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //세션 사용 안함
			
		//요청에 대한 권한 설정
		http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests
			.requestMatchers(new AntPathRequestMatcher("/oauth2/**")).permitAll() //해당 리소스에 대한 모든 요청 허가
			.anyRequest().authenticated());
			
		//oauth2 로그인
		http.oauth2Login(login->{
			login.userInfoEndpoint(user->user.userService(customOAuth2UserService))//OAuth2 인증 과정에서 Authentication 생성에 필요한 OAuth2User를 반황하는 클래스 지정
				.successHandler(oauth2AuthenticationSuccessHandler)
				.authorizationEndpoint(config->config.baseUri("/oauth2/authorization")) //소셜로그인 uri
				/*.authorizationRequestRepository(cookieAuthorizationRequestRepository) //인증 요청 쿠키에 저장
				.and()
				.redirectionEndpoint().baseUri("") //소셜 인증 후 uri
				.and()*/;
		//http.addFilterAfter(jwtAuthorizationFilter, JwtAuthenticationFilter.class);
		});

		return http.build();    
    }
	/*@Bean
	  public CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("http://localhost:8080");
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    return source;
	  }*/
	
}