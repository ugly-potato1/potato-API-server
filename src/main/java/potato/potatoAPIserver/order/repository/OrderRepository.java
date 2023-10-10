package potato.potatoAPIserver.order.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import potato.potatoAPIserver.order.domain.Order;
import potato.potatoAPIserver.user.domain.User;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
