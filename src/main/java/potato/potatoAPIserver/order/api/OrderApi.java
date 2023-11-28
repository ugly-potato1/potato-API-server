package potato.potatoAPIserver.order.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import potato.potatoAPIserver.common.ResponseForm;
import potato.potatoAPIserver.order.dto.request.OrderCreateRequest;
import potato.potatoAPIserver.order.dto.response.OrderCreateResponse;
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
}
