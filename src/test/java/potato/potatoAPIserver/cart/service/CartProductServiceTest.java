package potato.potatoAPIserver.cart.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.potatoAPIserver.cart.domain.Cart;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.cart.dto.request.AddToCartRequest;
import potato.potatoAPIserver.cart.repository.CartProductRepository;
import potato.potatoAPIserver.order.repository.ProductRepository;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.user.domain.User;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * @Author 허석문
 * @Since 2023-11-01
 */

@ExtendWith(MockitoExtension.class)
class CartProductServiceTest {

    @InjectMocks
    CartProductService sut;

    @Mock
    CartService cartService;

    @Mock
    CartProductRepository cartProductRepository;

    @Mock
    ProductRepository productRepository;


    @DisplayName("상품이 이미 장바구니에 존재하고 수량을 증가")
    @Test
    void testAddToCartExistingProduct() {
        // given
        Long userId = 1L;
        Long productId = 1L;
        AddToCartRequest request = new AddToCartRequest(productId, 2);

        User user = new User();
        Cart cart = new Cart();
        Product product = new Product();

        // 사용자가 이미 장바구니에 해당 상품을 가지고 있는 상황 모의
        CartProduct existingCartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(3) // 기존 수량
                .build();

        given(cartService.getCart(userId)).willReturn(Optional.of(cart));
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(cartProductRepository.findCart(cart.getId(), product.getId())).willReturn(Optional.of(existingCartProduct));

        // when
        sut.addToCart(userId, request);

        // then
        then(cartProductRepository).should().save(existingCartProduct);
        Assertions.assertThat(existingCartProduct.getQuantity()).isEqualTo(5); // 수량확인
    }

    @DisplayName("상품이 장바구니에 없어서 추가")
    @Test
    void testAddToCartNewProduct() {
        // given
        Long userId = 1L;
        AddToCartRequest request = new AddToCartRequest(1L, 2);

        User user = new User();
        Cart cart = new Cart();
        Product product = new Product();

        // 사용자의 장바구니에 해당 상품이 없는 상황 모의
        given(cartService.getCart(userId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(cartProductRepository.findCart(cart.getId(), product.getId())).willReturn(Optional.empty());

        // when
        sut.addToCart(userId, request);

        // then
        then(cartProductRepository).should().save(any(CartProduct.class));
    }


}
