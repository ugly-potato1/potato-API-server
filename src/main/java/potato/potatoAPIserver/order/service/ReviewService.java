package potato.potatoAPIserver.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
import potato.potatoAPIserver.order.domain.Review;
import potato.potatoAPIserver.order.dto.request.ReviewCreateRequest;
import potato.potatoAPIserver.order.dto.response.ReviewResponse;
import potato.potatoAPIserver.order.repository.OrderProductRepository;
import potato.potatoAPIserver.order.repository.ReviewRepository;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.product.repository.ProductRepository;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.service.UserService;

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
