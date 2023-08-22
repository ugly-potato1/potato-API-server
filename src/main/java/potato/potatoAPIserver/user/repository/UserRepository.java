package potato.potatoAPIserver.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.user.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String memberEmail);

    boolean existsById(Long Long);

    Optional<User> findByProviderId(String providerId);

    boolean existsByProviderId(String providerId);
}
