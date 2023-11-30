package potato.server.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartResponse {
    Long cartId;
    List<CartProductResponse> cartProductResponseList;

    public static CartResponse of(Long cartId, List<CartProductResponse> cartProductResponseList) {
        return new CartResponse(cartId, cartProductResponseList);
    }

}
