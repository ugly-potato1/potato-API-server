package potato.potatoAPIserver.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.product.dto.ProductDto;

@Getter
@AllArgsConstructor
public class CartProductResponse {
    private Long id;
    private ProductDto product;
    private int quantity;

    public static CartProductResponse from(CartProduct entity) {
        return new CartProductResponse(entity.getId(), ProductDto.from(entity.getProduct()), entity.getQuantity());
    }
}
