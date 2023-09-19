package potato.potatoAPIserver.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.user.domain.Policy;
import potato.potatoAPIserver.user.spec.NotificationAgree;

/**
 * @author 정순원
 * @since 2023-08-23
 */
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
