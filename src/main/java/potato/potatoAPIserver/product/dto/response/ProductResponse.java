package potato.potatoAPIserver.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author: 박건휘
 * @since: 2023-11-13
 */
@Builder
@Getter
@AllArgsConstructor
public class ProductResponse {
    private String title;
    private BigDecimal price;
    private String description;
    private Integer hit;
    private Integer stock;
    private Integer version;

    public static ProductResponse of(String title, BigDecimal price, String description, Integer stock, Integer version, Integer hit) {

        return ProductResponse.builder()
                .title(title)
                .price(price)
                .description(description)
                .stock(stock)
                .version(version)
                .hit(hit)
                .build();
    }

}
