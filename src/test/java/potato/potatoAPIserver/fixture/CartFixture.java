package potato.potatoAPIserver.fixture;

import potato.potatoAPIserver.cart.domain.Cart;
import potato.potatoAPIserver.cart.domain.CartProduct;

import java.lang.reflect.Field;

import static potato.potatoAPIserver.fixture.ProductFixture.createProduct;
import static potato.potatoAPIserver.fixture.UserFixture.*;

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

    public static Cart createCart(Long id) throws NoSuchFieldException, IllegalAccessException {
        Cart cart = Cart.builder()
                .user(createUser(1L))
                .build();

        Field idField = Cart.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(cart, id);

        return cart;
    }
}
