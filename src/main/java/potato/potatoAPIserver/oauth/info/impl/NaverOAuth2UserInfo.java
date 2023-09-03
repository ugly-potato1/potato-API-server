package potato.potatoAPIserver.oauth.info.impl;

import java.util.Map;

import potato.potatoAPIserver.oauth.info.OAuth2UserInfo;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {
	
	public NaverOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
    public String getId() {
        return (String) attributes.get("id");
    }
    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
    @Override
    public String getBirth() {
        return (String) attributes.get("birth");
    }
    @Override
    public String getGender() {
        return (String) attributes.get("gender");
    }
    @Override
    public String getNumber() {
        return (String) attributes.get("number");
    }
    @Override
    public String getNickName() {
        return (String) attributes.get("nickName");
    }
}
