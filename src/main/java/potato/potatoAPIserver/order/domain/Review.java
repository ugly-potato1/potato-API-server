package potato.potatoAPIserver.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.potatoAPIserver.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 500, nullable = false)
    private String content;

    @Column(nullable = false)
    private int evaluation;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImages = new ArrayList<ReviewImage>();

    @Builder
    public Review(Product product, User user, Sting content, int evaluation) {
        this.product = product;
        this.user = user;
        this.content = content;
        this.evaluation = evaluation;
    }
}
