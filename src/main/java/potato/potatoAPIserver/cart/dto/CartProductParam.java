package potato.potatoAPIserver.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.product.dto.ProductDto;

@Getter
@AllArgsConstructor
public class CartProductParam {
    private Long id;
    private ProductDto product;
    private int quantity;

    public static CartProductParam from(CartProduct entity) {
        return new CartProductParam(entity.getId(), ProductDto.from(entity.getProduct()), entity.getQuantity());
    }
}
