package potato.potatoAPIserver.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.cart.domain.Cart;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.cart.dto.request.AddToCartRequest;
import potato.potatoAPIserver.cart.repository.CartProductRepository;
import potato.potatoAPIserver.cart.repository.CartRepository;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
import potato.potatoAPIserver.order.repository.ProductRepository;
import potato.potatoAPIserver.product.domain.Product;
import potato.potatoAPIserver.user.domain.User;
import potato.potatoAPIserver.user.repository.UserRepository;

/**
 * @Author 허석문
 * @Since 2023-10-23
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartWriteService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public void addToCart(Long userId, AddToCartRequest request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(createCart(userId));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));

        CartProduct cartProduct = CartProduct
                .builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .build();

        cartProductRepository.save(cartProduct);
    }

    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.USER_NOT_FOUND));

        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }
}
