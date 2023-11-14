package potato.potatoAPIserver.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.product.domain.Wishlist;

import java.util.Optional;

/**
 * @Author 박건휘
 * @Since 2023-11-13
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUserId(Long userId);
}
