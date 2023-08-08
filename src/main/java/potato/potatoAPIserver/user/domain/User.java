package potato.potatoAPIserver.user.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	@Column(length = 50, nullable = false)
	private String email;
	
	@Column(length = 10, nullable = false)
	private String name;
	
	private LocalDate birth;
	
	enum Gender { M, F; }
	private Gender gender;
	
	enum Join_type { KAKAO, NAVER; }
	private Join_type join_type;
	
	@Column(length = 13, nullable = false)
	private String number;
	
	@Column(length = 50, nullable = false)
	private String nick_name;
	
	@OneToOne(mappedBy="user")
	private Notification notification;
	
	@Builder
	public User(String email, String name, LocalDate birth, Gender gender, Join_type join_type, String number, String nick_name) {
		this.email = email;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.join_type = join_type;
		this.number = number;
		this.nick_name = nick_name;
	}
}
