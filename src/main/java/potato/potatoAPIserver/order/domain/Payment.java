package potato.potatoAPIserver.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.potatoAPIserver.common.BaseTimeEntity;

/**
 * 외부 API 연결 시 바뀔 가능성 높음
 *
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int totalPrice;

    private int discountPrice;

    private int dliveryPrice;

    @Builder
    public Payment(Order order, int totalPrice, int discountPrice, int dliveryPrice) {
        this.order = order;
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
        this.dliveryPrice = dliveryPrice;
    }
}
