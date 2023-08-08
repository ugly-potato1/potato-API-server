package potato.potatoAPIserver.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "address")
public class Address extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(length = 30, nullable = false)
	private String name;
	
	@Column(length = 5, nullable = false)
	private String add_zipcode;
	
	@Column(length = 100, nullable = false)
	private String addr;
	
	@Column(length = 50, nullable = false)
	private String addr_detail;
	
	@Column(length = 13, nullable = false)
	private String addr_user_phone;
	
	enum Addr_default { Y, N; }
	private Addr_default addr_default;
	
	@Builder
	public Address(User user, String name, String add_zipcode, String addr, String addr_detail, String addr_user_phone, Addr_default addr_default) {
		this.user = user;
		this.name = name;
		this.add_zipcode = add_zipcode;
		this.addr = addr;
		this.addr_detail = addr_detail;
		this.addr_user_phone = addr_user_phone;
		this.addr_default = addr_default;
	}
}
