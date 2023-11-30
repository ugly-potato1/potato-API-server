package potato.potatoAPIserver.order.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.order.dto.request.PaymentCancleRequest;
import potato.potatoAPIserver.order.dto.request.PaymentCompleteRequest;
import potato.potatoAPIserver.order.dto.response.PaymentCompleteResponse;
import potato.potatoAPIserver.order.service.ImpPaymentService;
import potato.potatoAPIserver.order.service.PaymentService;
import potato.potatoAPIserver.security.auth.dto.AuthorityUserDTO;

/**
 * @author 정순원
 * @since 23-11-30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment")
public class PaymentApi {

    private final ImpPaymentService impPaymentService;
    private final PaymentService paymentService;

    //사전검증
    @PostMapping("/pre_validation/{order_id}")
    public ResponseForm preValidate(@PathVariable Long orderId) {
        impPaymentService.preValidate(orderId);
        return new ResponseForm<>();
    }

    //사후검증, 저장
    @PostMapping("/complete")
    public ResponseForm<PaymentCompleteResponse> paymentComplete(@RequestBody PaymentCompleteRequest paymentCompleteRequest, @AuthenticationPrincipal AuthorityUserDTO userDTO) throws Exception {
        return new ResponseForm<>(paymentService.paymentComplete(paymentCompleteRequest, userDTO));
    }

    @PostMapping
    public ResponseForm paymentCancle(@RequestBody PaymentCancleRequest paymentCancleRequest) {
        paymentService.paymentCancle(paymentCancleRequest);
        return new ResponseForm<>();
    }
}
