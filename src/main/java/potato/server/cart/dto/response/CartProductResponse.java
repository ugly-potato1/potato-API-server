<<<<<<<< HEAD:src/main/java/potato/server/cart/dto/response/CartProductResponse.java
package potato.server.cart.dto.response;
========
package potato.potatoAPIserver.cart.dto;
>>>>>>>> 773ed96 (주문 생성 API 구현 (#43)):src/main/java/potato/server/cart/dto/CartProductParam.java

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.cart.domain.CartProduct;
import potato.server.product.dto.ProductDto;

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
