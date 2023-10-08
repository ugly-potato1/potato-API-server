package potato.potatoAPIserver.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCreateRequest {

    private Long productId;
    private String content;
    private int evaluation;
}
