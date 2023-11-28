package potato.potatoAPIserver.product.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.potatoAPIserver.common.BaseTimeEntity;

import java.math.BigDecimal;

/**
 * @author 박건휘
 * @since 2023-08-09
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(length = 1000, nullable = false)
    private String description;

    private Integer hit;
    private Integer stock;

    @Version
    private Integer version;

    @Builder
    public Product(Category category, String title, BigDecimal price, String description, Integer stock) {
        this.category = category;
        this.title = title;
        this.price = price;
        this.description = description;
        this.stock = stock;
        this.version=0;
        this.hit = 0;
    }

    public void addHit(){
        this.hit++;
    }
}
