package potato.server.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import potato.server.common.BaseTimeEntity;
import potato.server.user.spec.Authority;
import potato.server.user.spec.Gender;
import potato.server.user.spec.JoinType;

import java.time.LocalDate;

/**
 * @author 정순원
 * @since 2023-09-18
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
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
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Authority authority = Authority.USER;

	@NotNull
	private String providerName;

	@NotNull
	private String providerId;

	private String profileImage;
	
	@Builder
	public User(String email, String name, LocalDate birth, Gender gender, JoinType joinType, String phoneNumber, String providerName, String providerId, String profileImage) {
		this.email = email;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.joinType = joinType;
		this.phoneNumber = phoneNumber;
		this.providerName = providerName;
		this.providerId = providerId;
		this.profileImage = profileImage;
	}

	public void updateProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public void updateEmail(String email) {
		this.email = email;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
