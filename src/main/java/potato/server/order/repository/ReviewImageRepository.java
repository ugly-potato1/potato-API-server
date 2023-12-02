package potato.server.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.order.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
