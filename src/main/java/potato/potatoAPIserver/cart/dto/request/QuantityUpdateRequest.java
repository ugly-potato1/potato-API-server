package potato.potatoAPIserver.cart.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuantityUpdateRequest {
    @Positive
    int quantity;
}
