package potato.potatoAPIserver.oauth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import potato.potatoAPIserver.user.domain.User;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User { //사용자 정보를 담음
    private User user;
    private Map<String, Object> attributes;

    public CustomUserDetails(User user) {
        this.user = user;
      }

      public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
      }

      // UserDetail Override
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
      }

      public Long getId() {
        return user.getId();
      }

      public String getEmail() {
        return user.getEmail();
      }

      @Override
      public String getUsername() {
        return String.valueOf(user.getId());
      }

      @Override
      public String getPassword() {
        return null;
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }

      // OAuth2User Override
      @Override
      public String getName() {
        return String.valueOf(user.getId());
      }

      @Override
      public Map<String, Object> getAttributes() {
        return attributes;
      }

      public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
      }
}