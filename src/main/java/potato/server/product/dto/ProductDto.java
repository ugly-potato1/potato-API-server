package potato.server.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import potato.server.product.domain.Product;

@Getter
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Integer price;
    private String description;
    private Integer hit;

    public static ProductDto from(Product entity) {
        return new ProductDto(entity.getId(), entity.getTitle(), entity.getPrice(), entity.getDescription(), entity.getHit());
    }
}
