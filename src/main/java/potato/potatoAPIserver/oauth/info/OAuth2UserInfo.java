package potato.potatoAPIserver.oauth.info;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class OAuth2UserInfo {
	protected Map<String, Object> attributes;
	
	public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getBirth();
    
    public abstract String getGender();
 
    public abstract String getNumber();
    
    public abstract String getNickName();

}
