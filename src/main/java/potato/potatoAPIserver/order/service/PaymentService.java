package potato.potatoAPIserver.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
import potato.potatoAPIserver.order.domain.Order;
import potato.potatoAPIserver.order.dto.request.PaymentCancleRequest;
import potato.potatoAPIserver.order.dto.request.PaymentCompleteRequest;
import potato.potatoAPIserver.order.dto.response.PaymentCompleteResponse;
import potato.potatoAPIserver.order.repository.OrderRepository;
import potato.potatoAPIserver.security.auth.dto.AuthorityUserDTO;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ImpPaymentService impPaymentService;
    private final OrderRepository orderRepository;

    public PaymentCompleteResponse paymentComplete(PaymentCompleteRequest request, AuthorityUserDTO userDTO) {
        //사후 검증
        BigDecimal amount = impPaymentService.paymentInfo(request.getImpUid());
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.ORDER_NOT_FOUND));
        //사후 검증에서 틀리면 결제 취소
        if (!order.getOrderPrice().equals(amount)) {
            //오류를 던져야 하나? 특정 값을 주어서 프론트에서 결제 취소API를 호출하게 해야하나
        }
        //TODO
        //사후 검증 통과하면
        //주문 상태 변경
        //cart에서 삭제
        // 결제 완료된 금액
        return new PaymentCompleteResponse();
    }


    public void paymentCancle(PaymentCancleRequest paymentCancleRequest) {
        //imp cancle 호출
        impPaymentService.refund(paymentCancleRequest.getOrderId(), paymentCancleRequest.getAmount(), paymentCancleRequest.getReason());
        //주문테이블에서 삭제
        //상품재고 추가
    }
}
