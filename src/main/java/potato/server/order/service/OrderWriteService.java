package potato.server.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.server.cart.domain.Cart;
import potato.server.cart.domain.CartProduct;
import potato.server.cart.repository.CartProductRepository;
import potato.server.cart.service.CartReadService;
import potato.server.common.CustomException;
import potato.server.common.ResultCode;
import potato.server.order.domain.Order;
import potato.server.order.dto.request.OrderCreateRequest;
import potato.server.order.repository.OrderRepository;
import potato.server.user.domain.User;
import potato.server.user.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderWriteService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderProductWriteService orderProductWriteService;
    private final CartReadService cartReadService;
    private final CartProductRepository cartProductRepository;

    public Long createOrderWithCart(Long userId, OrderCreateRequest orderCreateRequest) {
        Cart cart = cartReadService.findCart(userId).orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, ResultCode.CART_NOT_FOUND));

        List<Long> cartProductIdList = orderCreateRequest.getCartProductIdList();
        List<CartProduct> cartProductList = cartProductRepository.findAllByCartId(cart.getId());

        if (!cartProductList.stream().map(CartProduct::getId).collect(Collectors.toSet()).containsAll(cartProductIdList)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.CART_PRODUCT_NOT_FOUND);
        }

        List<CartProduct> selectedCartProductList = cartProductList.stream()
                .filter(cartProduct -> cartProductIdList.contains(cartProduct.getId()))
                .toList();

        BigDecimal totalPrice = getTotalPrice(selectedCartProductList);

        User user = userService.getUserById(userId);

        Order order = Order.builder()
                .user(user)
                .orderPrice(totalPrice)
                .build();

        Order savedOrder = orderRepository.save(order);

        orderProductWriteService.createOrderProductWithCart(userId, savedOrder, selectedCartProductList);

        return savedOrder.getId();
    }

    private static BigDecimal getTotalPrice(List<CartProduct> selectedCartProductList) {
        return selectedCartProductList.stream()
                .map(cartProduct -> cartProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
