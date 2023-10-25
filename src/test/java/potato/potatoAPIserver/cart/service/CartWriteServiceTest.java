package potato.potatoAPIserver.cart.service;

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
import potato.potatoAPIserver.cart.repository.CartRepository;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.order.repository.ProductRepository;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


/**
 * @Author 허석문
 * @Since 2023-10-25
 */
@ExtendWith(MockitoExtension.class)
class CartWriteServiceTest {

    @InjectMocks
    private CartWriteService cartWriteService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private CartProductRepository cartProductRepository;
    @Mock
    private ProductRepository productRepository;


    @DisplayName("정상 동작")
    @Test
    void testAddToCart() {
        // Given
        Long userId = 1L;
        AddToCartRequest request = new AddToCartRequest(1L, 2);

        User user = new User();
        Cart cart = new Cart();
        Product product = new Product();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(cartRepository.findByUserId(userId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));

        // When
        cartWriteService.addToCart(userId, request);

        // Then
        then(cartProductRepository).should().save(any(CartProduct.class));
    }

    @DisplayName("비정상 동작: 사용자가 없는 경우")
    @Test
    void testAddToCartWhenUserNotFound() {
        // Given
        Long userId = 1L;
        AddToCartRequest request = new AddToCartRequest(1L, 2);

        // Stubbing: Simulate the case where user is not found
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> cartWriteService.addToCart(userId, request))
                .isInstanceOf(CustomException.class);

        // Verify that save method is never called on cartProductRepository
        verify(cartProductRepository, never()).save(any(CartProduct.class));
    }

    @DisplayName("비정상 동작: 카트를 찾을 수 없는 경우")
    @Test
    void testAddToCartWhenCartNotFound() {
        // Given
        Long userId = 1L;
        AddToCartRequest request = new AddToCartRequest(1L, 2);

        User user = new User();

        // Stubbing: Simulate the case where cart is not found
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(cartRepository.findByUserId(userId)).willReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> cartWriteService.addToCart(userId, request))
                .isInstanceOf(CustomException.class);

        // Verify that save method is never called on cartProductRepository
        verify(cartProductRepository, never()).save(any(CartProduct.class));
    }

    @DisplayName("비정상 동작: 제품을 찾을 수 없는 경우")
    @Test
    void testAddToCartWhenProductNotFound() {
        // Given
        Long userId = 1L;
        AddToCartRequest request = new AddToCartRequest(1L, 2);

        User user = new User();
        Cart cart = new Cart();

        // Stubbing: Simulate the case where product is not found
        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(cartRepository.findByUserId(userId)).willReturn(Optional.of(cart));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> cartWriteService.addToCart(userId, request))
                .isInstanceOf(CustomException.class);

        // Verify that save method is never called on cartProductRepository
        verify(cartProductRepository, never()).save(any(CartProduct.class));
    }

}
