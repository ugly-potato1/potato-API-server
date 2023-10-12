package potato.potatoAPIserver.product.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: 박건휘
 * @since: 2023-10-08
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCreateRequest {
    private String title;
    private Integer price;
    private String description;

    public ProductCreateRequest(String title, Integer price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }
}
