package potato.potatoAPIserver.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.potatoAPIserver.product.domain.Product;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer hit;

    public static ProductDto from(Product entity) {
        return new ProductDto(entity.getId(), entity.getTitle(), entity.getPrice(), entity.getDescription(), entity.getHit());
    }
}
