package potato.server.order.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.order.dto.request.OrderCreateRequest;
import potato.potatoAPIserver.order.dto.response.OrderCreateResponse;
import potato.potatoAPIserver.order.dto.response.OrderResponse;
import potato.potatoAPIserver.order.service.OrderReadService;
import potato.potatoAPIserver.order.service.OrderWriteService;
import potato.potatoAPIserver.security.auth.dto.AuthorityUserDTO;

/**
 * @author: 허석문
 * @since: 2023-11-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orders/")
public class OrderApi {

    private final OrderWriteService orderWriteService;
    private final OrderReadService orderReadService;

    @PostMapping
    public ResponseForm<OrderCreateResponse> createOrderWithCart(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @Valid @RequestBody OrderCreateRequest orderCreateRequest
    ) {

        Long order = orderWriteService.createOrderWithCart(userDTO.getId(), orderCreateRequest);

        return new ResponseForm<>(
                new OrderCreateResponse(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(order)
                        .toUri())
        );
    }

    @GetMapping("/{order-id}")
    public ResponseForm<OrderResponse> findOrder(
            @AuthenticationPrincipal AuthorityUserDTO userDTO,
            @PathVariable("order-id") Long orderId

    ) {
        return new ResponseForm<>(orderReadService.findOrder(userDTO.getId(), orderId));
    }
}
