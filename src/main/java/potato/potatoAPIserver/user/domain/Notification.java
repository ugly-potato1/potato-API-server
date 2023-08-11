package potato.potatoAPIserver.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import potato.potatoAPIserver.common.BaseTimeEntity;

/**
 * @author 김서인
 * @since 2023-08-08
 */

@Getter
@NoArgsConstructor
@Entity
public class Notification extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private NotificationAgree notificationAgree;
	
	@Builder
	public Notification(User user, NotificationAgree notificationAgree) {
		this.user = user;
		this.notificationAgree = notificationAgree;
	}

}
