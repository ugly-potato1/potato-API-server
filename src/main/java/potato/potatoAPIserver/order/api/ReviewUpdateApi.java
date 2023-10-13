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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/review")
public class ReviewUpdateApi {

    private ReviewService reviewService;


    @PostMapping
    public ResponseForm<ReviewResponse> createReview(@AuthenticationPrincipal AuthorityUserDTO userDTO, @RequestBody ReviewCreateRequest Request) {
        return new ResponseForm<>(reviewService.createReview(Request, userDTO.getId()));
    }
}
