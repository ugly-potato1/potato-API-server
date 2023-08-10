package potato.potatoAPIserver.user.domain;

import java.time.LocalDate;

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
@Table(name = "users")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;
	
	@Column(length = 50, nullable = false)
	private String email;
	
	@Column(length = 10, nullable = false)
	private String name;
	
	private LocalDate birth;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private JoinType joinType;
	
	@Column(length = 13, nullable = false)
	private String number;
	
	@Column(length = 50, nullable = false)
	private String nickName;
	
	@OneToOne(mappedBy="user")
	private Notification notification;
	
	@Builder
	public User(String email, String name, LocalDate birth, Gender gender, JoinType joinType, String number, String nickName) {
		this.email = email;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.joinType = joinType;
		this.number = number;
		this.nickName = nickName;
	}
}
