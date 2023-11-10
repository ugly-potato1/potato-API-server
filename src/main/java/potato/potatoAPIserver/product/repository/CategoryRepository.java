package potato.potatoAPIserver.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.product.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

