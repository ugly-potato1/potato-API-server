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
public class Category extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String division;

    @Column(length = 100, nullable = false)
    private String subdivision;

    @Builder
    public Category(String division, String subdivision) {
        this.division = division;
        this.subdivision = subdivision;
    }
}
