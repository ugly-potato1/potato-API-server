package potato.server.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author: 박건휘
 * @since: 2023-10-08
 */
@Getter
@AllArgsConstructor
public class ProductCreateRequest {
    @NotBlank
    private String title;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String description;
}
