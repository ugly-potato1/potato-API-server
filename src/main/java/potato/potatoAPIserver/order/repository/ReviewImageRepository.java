package potato.potatoAPIserver.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.order.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
