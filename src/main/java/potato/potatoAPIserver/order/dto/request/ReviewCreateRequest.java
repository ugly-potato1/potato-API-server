package potato.potatoAPIserver.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
@Data
@AllArgsConstructor
public class ReviewCreateRequest {

    private Long productId;
    private String content;
    private int evaluation;
}
