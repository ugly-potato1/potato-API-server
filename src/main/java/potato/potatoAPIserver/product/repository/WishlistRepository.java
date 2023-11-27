package potato.potatoAPIserver.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.product.domain.Wishlist;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByUserId(Long userId);
}
