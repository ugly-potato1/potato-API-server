package potato.potatoAPIserver.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    private Integer price;
    @NotBlank
    private String description;
}
