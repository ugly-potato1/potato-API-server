package potato.potatoAPIserver.oauth.info;

import java.util.Map;
import potato.potatoAPIserver.user.domain.JoinType;
import potato.potatoAPIserver.oauth.info.impl.NaverOAuth2UserInfo;
import potato.potatoAPIserver.oauth.info.impl.KakaoOAuth2UserInfo;

public class OAuth2UserInfoFactory {

	private OAuth2UserInfoFactory() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static OAuth2UserInfo getOAuth2UserInfo(JoinType joinType, Map<String, Object> attributes) {
		if (joinType == JoinType.NAVER) {
		    return new NaverOAuth2UserInfo(attributes);
		}
		if (joinType == JoinType.KAKAO) {
			return new KakaoOAuth2UserInfo(attributes);
		}
		throw new IllegalArgumentException("Invalid AuthProvider Type.");
	}
}
