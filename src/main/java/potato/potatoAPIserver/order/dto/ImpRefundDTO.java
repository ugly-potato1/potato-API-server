package potato.potatoAPIserver.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 정순원
 * @since 23-11-30
 */
@Data
@AllArgsConstructor
public class ImpRefundDTO {

    private String merchant_uid;
    private BigDecimal amount;
    private String reason;
}
