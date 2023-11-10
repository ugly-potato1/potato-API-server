package potato.potatoAPIserver.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.product.domain.SubCategory;
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
}

