package potato.potatoAPIserver.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.product.dto.ProductParam;

@Getter
@AllArgsConstructor
public class CartProductParam {
    private Long id;
    private ProductParam product;
    private int quantity;

    public static CartProductParam from(CartProduct entity) {
        return new CartProductParam(entity.getId(), ProductParam.from(entity.getProduct()), entity.getQuantity());
    }
}
