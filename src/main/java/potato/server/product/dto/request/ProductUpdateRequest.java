package potato.server.product.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * @author: 박건휘
 * @since: 2024-01-14
 */
@Data
@NoArgsConstructor
public class ProductUpdateRequest {

    @NotNull
    private Long productId;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer stock;
}
