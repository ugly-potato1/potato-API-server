package potato.potatoAPIserver.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.potatoAPIserver.order.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
