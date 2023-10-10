package potato.potatoAPIserver.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.order.domain.Review;
import potato.potatoAPIserver.order.dto.request.ReviewCreateRequest;
import potato.potatoAPIserver.order.repository.OrderProductRepository;
import potato.potatoAPIserver.order.repository.ProductRepository;
import potato.potatoAPIserver.order.repository.ReviewRepository;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Review createReview(ReviewCreateRequest request, long userId) {
        boolean hasPurchased = orderProductRepository.existsByProductAndUser(request.getProductId(), userId);
        if (!hasPurchased) {
            throw new RuntimeException("유저가 이 상품을 구매하지 않았습니다."); //TODO 예외 코드 정의후 구형
        }
        User user = userRepository.findById(userId).orElseThrow(); //TODO
        Product product = productRepository.findById(request.getProductId()).orElseThrow();//TODO
        Review review = Review.builder()
                .product(product)
                .user(user)
                .content(request.getContent())
                .evaluation(request.getEvaluation())
                .build();

        return reviewRepository.save(review); //TODO 리뷰이미지 추가 예정
    }

}
