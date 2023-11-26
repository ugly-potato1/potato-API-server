package potato.potatoAPIserver.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.cart.dto.CartProductParam;
import potato.potatoAPIserver.cart.repository.CartProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartProductReadService {

    private final CartProductRepository cartProductRepository;

    public List<CartProductParam> findCartProductList(Long cartId) {
        List<CartProduct> cartProductList = cartProductRepository.findAllByCartId(cartId);

        if (cartProductList.isEmpty()) {
            return List.of();
        }

        return cartProductList
                .stream().map(CartProductParam::from)
                .toList();
    }
}
