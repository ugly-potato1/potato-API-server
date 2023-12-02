package potato.server.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import potato.server.common.BaseTimeEntity;
import potato.server.user.spec.AddrDefault;

/**
 * @author 김서인
 * @since 2023-08-08
 */
@Getter
@NoArgsConstructor
@Entity
public class Address extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(length = 30, nullable = false)
	private String name;
	
	@Column(length = 5, nullable = false)
	private String addZipcode;
	
	@Column(length = 100, nullable = false)
	private String addr;
	
	@Column(length = 50, nullable = false)
	private String addrDetail;
	
	@Column(length = 13, nullable = false)
	private String addrUserPhone;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private AddrDefault addrDefault;
	
	@Builder
	public Address(User user, String name, String addZipcode, String addr, String addrDetail, String addrUserPhone, AddrDefault addrDefault) {
		this.user = user;
		this.name = name;
		this.addZipcode = addZipcode;
		this.addr = addr;
		this.addrDetail = addrDetail;
		this.addrUserPhone = addrUserPhone;
		this.addrDefault = addrDefault;
	}
}
