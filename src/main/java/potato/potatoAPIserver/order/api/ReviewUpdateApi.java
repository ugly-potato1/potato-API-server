package potato.potatoAPIserver.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.order.dto.request.ReviewCreateRequest;
import potato.potatoAPIserver.order.service.ReviewService;
import potato.potatoAPIserver.security.auth.dto.AuthorityUserDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class ReviewUpdateApi {

    private ReviewService reviewService;


    @PostMapping
    public ResponseEntity createReview(@AuthenticationPrincipal AuthorityUserDTO userDTO,@RequestBody ReviewCreateRequest Request) {
        reviewService.createReview(Request, userDTO.getId());
        return new ResponseEntity<>(HttpStatusCode.valueOf(200)); //TODO 공통 폼
    }
}
