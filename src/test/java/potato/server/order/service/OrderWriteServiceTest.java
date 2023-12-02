package potato.server.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import potato.server.cart.domain.Cart;
import potato.server.cart.domain.CartProduct;
import potato.server.cart.repository.CartProductRepository;
import potato.server.cart.service.CartReadService;
import potato.server.order.domain.Order;
import potato.server.order.dto.request.OrderCreateRequest;
import potato.server.order.repository.OrderRepository;
import potato.server.user.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static potato.server.fixture.CartFixture.creatCartProduct;
import static potato.server.fixture.OrderFixture.createOrder;

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
        given(orderRepository.save(any(Order.class))).willAnswer(invocation -> createOrder(1L, userId));
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




}
