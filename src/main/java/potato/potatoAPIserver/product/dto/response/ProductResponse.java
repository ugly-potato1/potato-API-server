package potato.potatoAPIserver.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author: 박건휘
 * @since: 2023-11-13
 */
@Builder
@Getter
@AllArgsConstructor
public class ProductResponse {
    private String title;
    private Integer price;
    private String description;

    public static ProductResponse of(String title, Integer price, String description) {

        return ProductResponse.builder()
                .title(title)
                .price(price)
                .description(description)
                .build();
    }

}
