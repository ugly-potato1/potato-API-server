package potato.potatoAPIserver.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author 정순원
 * @Since 2023-10-12
 */
@Data
@AllArgsConstructor
public class ReviewCreateRequest {

    @NotNull
    private Long productId;
    private String content;
    @NotNull
    private int evaluation;
    private String fileName;
}
