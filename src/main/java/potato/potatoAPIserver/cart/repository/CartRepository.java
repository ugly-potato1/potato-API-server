package potato.potatoAPIserver.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.cart.domain.Cart;

import java.util.Optional;

/**
 * @Author 허석문
 * @Since 2023-10-23
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}
