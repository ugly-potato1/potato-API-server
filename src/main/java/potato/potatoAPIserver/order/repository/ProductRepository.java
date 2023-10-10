package potato.potatoAPIserver.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
