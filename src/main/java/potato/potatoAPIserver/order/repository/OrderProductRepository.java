package potato.potatoAPIserver.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import potato.potatoAPIserver.order.domain.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT COUNT(op) > 0 FROM OrderProduct op WHERE op.product.id = :productId AND op.order.user.id = :userId")
    boolean existsByProductAndUser(@Param("productId") Long productId, @Param("userId") Long userId);

}
