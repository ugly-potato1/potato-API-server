package potato.server.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import potato.server.review.domain.Review;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {

    void deleteById(long reviewId);

    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.user.id = :userId AND r.id = :reviewId")
    boolean existsByUserIdAndReviewId(@Param("userId") long userId, @Param("reviewId") long reviewId);
}

