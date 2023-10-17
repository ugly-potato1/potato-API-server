package potato.potatoAPIserver.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.order.dto.request.ReviewCreateRequest;
import potato.potatoAPIserver.order.dto.response.ReviewResponse;
import potato.potatoAPIserver.order.service.ReviewService;
import potato.potatoAPIserver.security.auth.dto.AuthorityUserDTO;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class ReviewUpdateApi {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseForm<ReviewResponse> createReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @RequestBody ReviewCreateRequest request) {
        return new ResponseForm<>(reviewService.createReview(request, userDTO.getId()));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseForm removeReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @PathVariable long reviewId) {
        reviewService.removeReview(userDTO.getId(), reviewId);
        return new ResponseForm<>();
    }
}
