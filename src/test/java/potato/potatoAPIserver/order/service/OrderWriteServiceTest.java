package potato.potatoAPIserver.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.potatoAPIserver.cart.domain.Cart;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.cart.repository.CartProductRepository;
import potato.potatoAPIserver.cart.service.CartReadService;
import potato.potatoAPIserver.order.domain.Order;
import potato.potatoAPIserver.order.dto.request.OrderCreateRequest;
import potato.potatoAPIserver.order.repository.OrderRepository;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.user.service.UserService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("주문 서비스 테스트 - 쓰기")
@ExtendWith(MockitoExtension.class)
class OrderWriteServiceTest {

    @InjectMocks
    private OrderWriteService sut;

    @Mock
    private UserService userService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductWriteService orderProductWriteService;
    @Mock
    private CartReadService cartReadService;
    @Mock
    private CartProductRepository cartProductRepository;


    @DisplayName("새로운 주문을 생성한다.")
    @Test
    void createOrder_shouldCreateOrderSuccessfully() throws IllegalAccessException, NoSuchFieldException {
        // Given
        Long userId = 1L;
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(Arrays.asList(1L, 2L));

        Cart cart = Cart.builder().build();
        given(cartReadService.findCart(userId)).willReturn(Optional.of(cart));

        List<CartProduct> cartProductList = Arrays.asList(
                creatCartProduct(1L),
                creatCartProduct(2L),
                creatCartProduct(3L)
        );
        given(cartProductRepository.findAllByCartId(cart.getId())).willReturn(cartProductList);
        given(orderRepository.save(any(Order.class))).willAnswer(invocation -> createOrder(1L));
        willDoNothing().given(orderProductWriteService).createOrderProductWithCart(any(Long.class), any(Order.class), anyList());

        // When
        Long orderId = sut.createOrderWithCart(userId, orderCreateRequest);

        // Then
        then(cartReadService).should(times(1)).findCart(userId);
        then(cartProductRepository).should(times(1)).findAllByCartId(cart.getId());
        then(userService).should(times(1)).getUserById(userId);
        then(orderRepository).should(times(1)).save(any(Order.class));
        then(orderProductWriteService).should(times(1)).createOrderProductWithCart(any(Long.class), any(Order.class), anyList());

        assertThat(orderId).isNotNull().isEqualTo(1L); // 생성된 ID가 예상대로 반환되었는지 확인
    }


    private Product createProduct(Long id) throws NoSuchFieldException, IllegalAccessException {
        Product product = Product.builder()
                .price(BigDecimal.ONE)
                .build();

        Field idField = Product.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(product, id);

        return product;
    }

    private CartProduct creatCartProduct(Long id) throws NoSuchFieldException, IllegalAccessException {
        CartProduct cartProduct = CartProduct.builder()
                .quantity(5)
                .product(createProduct(id))
                .build();

        Field idField = CartProduct.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(cartProduct, id);

        return cartProduct;
    }

    private Order createOrder(Long id) throws NoSuchFieldException, IllegalAccessException {
        Order order = Order.builder().build();

        Field idField = Order.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(order, id);

        return order;
    }
}
