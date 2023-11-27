package potato.potatoAPIserver.cart.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import potato.potatoAPIserver.cart.dto.request.CartProductCreateRequest;
import potato.potatoAPIserver.cart.dto.request.QuantityUpdateRequest;
import potato.potatoAPIserver.cart.dto.response.CartResponse;
import potato.potatoAPIserver.cart.service.CartProductWriteService;
import potato.potatoAPIserver.cart.service.CartReadService;
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

    private final CartReadService cartReadService;
    private final CartProductWriteService cartProductWriteService;

    @GetMapping
    public ResponseForm<CartResponse> findCartInfo(@AuthenticationPrincipal AuthorityUserDTO userDTO) {
        return new ResponseForm<>(cartReadService.findCartInfo(userDTO.getId()));
    }


    @PostMapping("/products")
    public ResponseForm<Void> addProductToCart(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @Valid @RequestBody CartProductCreateRequest request
    ) {
        cartProductWriteService.createCartProduct(userDTO.getId(), request);
        return new ResponseForm<>();
    }

    @PatchMapping("/products/{cart-product-id}")
    public ResponseForm<Void> updateQuantity(
            @PathVariable("cart-product-id") Long cartProductId,
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @Valid QuantityUpdateRequest quantityUpdateRequest
    ) {
        cartProductWriteService.updateQuantity(userDTO.getId(), cartProductId, quantityUpdateRequest.getQuantity());
        return new ResponseForm<>();
    }

    @DeleteMapping("/products/{cart-product-id}")
    public ResponseForm<Void> deleteCartProduct(
            @PathVariable("cart-product-id") Long cartProductId,
            @AuthenticationPrincipal AuthorityUserDTO userDTO
    ) {
        cartProductWriteService.deleteCartProduct(cartProductId, userDTO.getId());
        return new ResponseForm<>();
    }

}
