package potato.potatoAPIserver.cart.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import potato.potatoAPIserver.cart.dto.request.AddToCartRequest;
import potato.potatoAPIserver.cart.service.CartWriteService;
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

    private final CartWriteService cartWriteService;

    @PostMapping("/products")
    public ResponseForm<Void> addProductToCart(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @Valid @RequestBody AddToCartRequest request
    ) {
        cartWriteService.addToCart(userDTO.getId(), request);
        return new ResponseForm<>();
    }
}
