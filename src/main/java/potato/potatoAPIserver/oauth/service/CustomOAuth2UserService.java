package potato.potatoAPIserver.oauth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import potato.potatoAPIserver.user.domain.JoinType;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.oauth.CustomUserDetails;
import potato.potatoAPIserver.oauth.exception.OAuthProcessingException;
import potato.potatoAPIserver.user.repository.UserRepository;
import potato.potatoAPIserver.oauth.info.OAuth2UserInfo;
import potato.potatoAPIserver.oauth.info.OAuth2UserInfoFactory;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService { //사용자 정보를 가져옴
	
	@Autowired
	private final UserRepository userRepository;
	
	//OAuth2UserRequest에 있는 Access Token으로 유저정보 get
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);

	    try {
	      return processOAuth2User(userRequest, oauth2User);
	    } catch (AuthenticationException e) {
	      throw e;
	    } catch (Exception e) {
	      throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
	    }
	}
	
	// 획득한 유저정보를 Java Model과 맵핑하고 프로세스 진행
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
    	JoinType joinType = JoinType.valueOf(
    	        userRequest.getClientRegistration().getRegistrationId().toUpperCase());
    	    OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(joinType,
    	        oAuth2User.getAttributes());

    	    if (userInfo.getEmail().isEmpty()) {
    	      throw new OAuthProcessingException("Email not found from OAuth2 provider");
    	    }
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());
        
        User user;
        if (userOptional.isPresent()) {		// 이미 가입된 경우
            user = userOptional.get();
            if (joinType != user.getJoinType()) {
                throw new OAuthProcessingException("Wrong Match Auth Provider");
            }

        } else {			// 가입되지 않은 경우
            user = createUser(userInfo, joinType);
        }
        return new CustomUserDetails(user, oAuth2User.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, JoinType joinType) {
        return userRepository.save(
          User.builder()
            .email(userInfo.getEmail())
            .name(userInfo.getName())
            /*.profileImage(userInfo.getImageUrl())
            .role(Role.USER)*/
            .joinType(joinType)
            .build()
        );
      }
}
