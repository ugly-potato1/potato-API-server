package potato.potatoAPIserver.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import potato.potatoAPIserver.common.BaseTimeEntity;

/**
 * @author 박건휘
 * @since 2023-08-09
 */
@Entity
@Getter
@NoArgsConstructor
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
    private int price;

    @Column(length = 1000, nullable = false)
    private String description;

    private int hit;

    @Builder
    public Product(Category category, String title, int price, String description, int hit) {
        this.category = category;
        this.title = title;
        this.price = price;
        this.description = description;
        this.hit = hit;
    }
}
