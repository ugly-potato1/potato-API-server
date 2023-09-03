package potato.potatoAPIserver.oauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import potato.potatoAPIserver.oauth.CustomUserDetails;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService { //TokenFilter에서 DB로 인증 받기 위한 클래스

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    User user = userRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
    return new CustomUserDetails(user);
  }
}
