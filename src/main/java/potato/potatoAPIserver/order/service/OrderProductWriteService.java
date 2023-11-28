package potato.potatoAPIserver.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.cart.service.CartProductWriteService;
import potato.potatoAPIserver.order.domain.Order;
import potato.potatoAPIserver.order.domain.OrderProduct;
import potato.potatoAPIserver.order.repository.OrderProductRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProductWriteService {

    private final OrderProductRepository orderProductRepository;
    private final CartProductWriteService cartProductWriteService;

    public void createOrderProductWithCart(Long userId, Order order, List<CartProduct> cartProductParamList) {
        cartProductParamList.forEach(c -> {
            orderProductRepository.save(
                    OrderProduct.builder()
                            .order(order)
                            .product(c.getProduct())
                            .price(c.getProduct().getPrice())
                            .build());

            cartProductWriteService.deleteCartProduct(userId, c.getId());
        });
    }

}
