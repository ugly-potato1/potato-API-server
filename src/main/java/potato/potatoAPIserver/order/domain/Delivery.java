package potato.potatoAPIserver.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import potato.potatoAPIserver.common.BaseTimeEntity;

/**
 * @author 정순원
 * @since 2023-08-08
 */
@Entity
@Getter
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dliver_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(length = 50, nullable = false)
    private String dliveryStatus;

    private int orderNumber;

    @Column(length = 100)
    private String shipper;

    @Builder
    public Delivery(Order order, String dliveryStatus, int orderNumber, String shipper) {
        this.order = order;
        this.dliveryStatus = dliveryStatus;
        this.orderNumber = orderNumber;
        this.shipper = shipper;
    }
}
