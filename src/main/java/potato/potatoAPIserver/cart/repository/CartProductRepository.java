package potato.potatoAPIserver.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.cart.domain.CartProduct;

/**
 * @Author 허석문
 * @Since 2023-10-23
 */
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
