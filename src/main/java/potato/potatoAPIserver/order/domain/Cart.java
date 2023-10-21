package potato.potatoAPIserver.order.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.potatoAPIserver.common.BaseTimeEntity;
import potato.potatoAPIserver.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }
}
