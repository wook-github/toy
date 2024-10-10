package com.wook.toy.domain;

import java.math.BigDecimal;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="t_user", schema="toy", catalog = "toy")
@DynamicUpdate
@SequenceGenerator(name = "Member_Seq_Generator", sequenceName = "toy.t_user_seq", initialValue = 1, allocationSize = 1)
public class Member {

	@Id
	@Column(name = "user_number", columnDefinition = "NUMERIC(15,0)")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Member_Seq_Generator")
	private BigDecimal userNumber;
	
	@Column(name = "user_id", columnDefinition = "VARCHAR(256)")
	private String userId;
	
	@Column(name = "user_password", columnDefinition = "VARCHAR(256)")
	private String userPassword;
	
	@Column(name = "user_name", columnDefinition = "VARCHAR(32)")
	private String userName;
	
	@Column(name = "user_nickname", columnDefinition = "VARCHAR(256)")
	private String userNickname;
	
	@Column(name = "user_phone", columnDefinition = "VARCHAR(32)")
	private String userPhone;
	
	@Column(name = "user_email", columnDefinition = "VARCHAR(128)")
	private String userEmail;
	
	@Column(name = "user_birth", columnDefinition = "BPCHAR(8)")
	private String userBirth;
	
	@Column(name = "user_role", columnDefinition = "VARCHAR(16)")
	private String userRole;
	
	@Column(name = "join_dt", columnDefinition = "BPCHAR(14)")
	@ColumnDefault("to_char(now(), 'YYYYMMDD24HHMISS')")
	private String joinDt;
	
	@Column(name = "last_login_dt", columnDefinition = "BPCHAR(14)")
	@ColumnDefault("to_char(now(), 'YYYYMMDD24HHMISS')")
	private String lastLoginDt;
	
	@Column(name = "use_yn", columnDefinition = "BPCHAR(1)")
	@ColumnDefault("'Y'")
	private String useYn;
	
	@Column(name = "updt_ymd", columnDefinition = "BPCHAR(8)")
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String updtYmd;
	
	@Column(name = "updusr_id", columnDefinition = "BPCHAR(32)")
	@ColumnDefault("'ADMIN'")
	private String updusrId;
	
	@Column(name = "updusr_ip", columnDefinition = "BPCHAR(32)")
	@ColumnDefault("'127.0.0.1'")
	private String updusrIp;
	
	@Column(name = "rmks", columnDefinition = "VARCHAR(512)")
	private String rmks;
	
	public static Member createMember() {
		return new Member();
	}

}
