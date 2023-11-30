package potato.potatoAPIserver.order.dto.request;

import lombok.Data;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Data
public class PaymentCompleteRequest {

    private String impUid;
    private Long orderId;
    private Long totalPrice;
}
