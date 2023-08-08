package potato.potatoAPIserver.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "notification")
public class Notification extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private int id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	enum Notification_agree { Y, N; }
	private Notification_agree notification_agree;
	
	@Builder
	public Notification(User user, Notification_agree notification_agree) {
		this.user = user;
		this.notification_agree = notification_agree;
	}

}
