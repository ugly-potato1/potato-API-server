package potato.potatoAPIserver.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author 박건휘
 * @Since 2023-11-13
 */
@Getter
@AllArgsConstructor
public class WishlistProductCreateRequest {
    @NotNull
    private Long productId;
}
