package potato.potatoAPIserver.cart.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import potato.potatoAPIserver.cart.domain.Cart;
import potato.potatoAPIserver.cart.domain.CartProduct;
import potato.potatoAPIserver.cart.dto.request.AddToCartRequest;
import potato.potatoAPIserver.cart.repository.CartProductRepository;
import potato.potatoAPIserver.common.CustomException;
import potato.potatoAPIserver.common.ResultCode;
import potato.potatoAPIserver.order.repository.ProductRepository;
import potato.potatoAPIserver.product.domain.Product;

/**
 * @Author 허석문
 * @Since 2023-11-01
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CartProductService {

    private final CartService cartService;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public void addToCart(Long userId, AddToCartRequest request) {
        Cart cart = cartService.getCart(userId).orElse(
                cartService.createCart(userId)
        );

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.PRODUCT_NOT_FOUND));

        CartProduct cartProduct = cartProductRepository.findCart(cart.getId(), product.getId()).orElse(null);

        if (cartProduct != null) {
            // 상품이 이미 장바구니에 존재하는 경우
            cartProduct.addQuantity(request.getQuantity());
        } else {
            // 상품이 장바구니에 없는 경우
            cartProduct = CartProduct
                    .builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }

        cartProductRepository.save(cartProduct);
    }
}
