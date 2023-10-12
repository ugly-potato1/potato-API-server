package potato.potatoAPIserver.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.order.dto.request.ReviewCreateRequest;
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
    public ResponseEntity createReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @RequestBody ReviewCreateRequest Request) {
        reviewService.createReview(Request, userDTO.getId());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200)); //TODO 공통 폼
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity removeReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @PathVariable long reviewId) {
        reviewService.removeReview(userDTO.getId(), reviewId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200)); //TODO 공통 폼
    }
}
