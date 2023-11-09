package potato.potatoAPIserver.cart.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.cart.dto.request.AddToCartRequest;
import potato.potatoAPIserver.cart.service.CartProductService;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.security.auth.dto.AuthorityUserDTO;

/**
 * @Author 허석문
 * @Since 2023-10-23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/carts")
public class CartApi {

    private final CartProductService cartProductService;

    @PostMapping("/products")
    public ResponseForm<Void> addProductToCart(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @Valid @RequestBody AddToCartRequest request
    ) {
        cartProductService.addToCart(userDTO.getId(), request);
        return new ResponseForm<>();
    }

    @PatchMapping("/products/{cart-product-id}")
    public ResponseForm<Void> increaseQuantity(
            @PathVariable("cart-product-id") Long cartProductId,
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @RequestBody int quantity
    ) {
        cartProductService.updateQuantity(userDTO.getId(), cartProductId, quantity);
        return new ResponseForm<>();
    }

}
