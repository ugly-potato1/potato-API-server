package potato.potatoAPIserver.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Author 허석문
 * @Since 2023-10-23
 */
@Getter
@AllArgsConstructor
public class AddToCartRequest {
    @NotNull
    private Long productId;
    @Min(1)
    private int quantity;
}
