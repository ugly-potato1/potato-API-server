package potato.server.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.domain.Review;
import potato.server.order.dto.request.ReviewCreateRequest;
import potato.server.order.dto.response.ReviewResponse;
import potato.server.order.repository.OrderProductRepository;
import potato.server.order.repository.ReviewRepository;
import potato.server.product.domain.Product;
import potato.server.product.repository.ProductRepository;
import potato.server.user.domain.User;
import potato.server.user.service.UserService;

/**
 * @author 정순원
 * @since 2023-10-13
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public ReviewResponse createReview(ReviewCreateRequest request, long userId) {
        boolean hasPurchased = orderProductRepository.existsByProductAndUser(request.getProductId(), userId);
        if (!hasPurchased) {
            throw new CustomException(HttpStatus.NOT_FOUND, ResultCode.ORDER_NOT_FOUND);
        }
        User user = userService.getUserById(userId);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));
        Review review = Review.builder()
                .product(product)
                .user(user)
                .content(request.getContent())
                .evaluation(request.getEvaluation())
                .build();
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponse(savedReview.getId()); //TODO 리뷰이미지 추가 예정
    }

    @Transactional
    public void removeReview(long userId, long reviewId) {
        isWriter(userId, reviewId);
        reviewRepository.deleteById(reviewId);
    }

    private void isWriter(long userId, long reviewId) {
        boolean tf = reviewRepository.existsByUserIdAndReviewId(userId, reviewId);

        if (tf == false) {
            throw new RuntimeException("리뷰 작성자가 아닙니다."); //TODO 예외 코드 정의후 구형
        }
    }


}
