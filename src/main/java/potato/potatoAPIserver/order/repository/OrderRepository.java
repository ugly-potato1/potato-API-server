package potato.potatoAPIserver.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.order.domain.Order;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
public interface OrderRepository extends JpaRepository<Order, Long> {


}
