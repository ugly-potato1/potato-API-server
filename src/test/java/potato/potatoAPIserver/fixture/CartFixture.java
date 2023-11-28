package potato.potatoAPIserver.fixture;

import potato.potatoAPIserver.cart.domain.CartProduct;

import java.lang.reflect.Field;

import static potato.potatoAPIserver.fixture.ProductFixture.createProduct;

public class CartFixture {
    public static CartProduct creatCartProduct(Long id) throws NoSuchFieldException, IllegalAccessException {
        CartProduct cartProduct = CartProduct.builder()
                .quantity(5)
                .product(createProduct(id))
                .build();

        Field idField = CartProduct.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(cartProduct, id);

        return cartProduct;
    }
}
