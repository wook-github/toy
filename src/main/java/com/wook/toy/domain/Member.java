package com.wook.toy.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;
	
	private String userId;
	private String userPassword;
	private String userRoles;
	
	public Long getUserNo() {
		return userNo;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public String getUserRoles() {
		return userRoles;
	}

	protected Member() {}
	
	private Member(Long userNo, String userId, String userPassword, String userRoles) {
		this.userNo = userNo;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userRoles = userRoles;
	}
	
	public static Member createMember(String userId, String userPassword, PasswordEncoder passwordEncoder) {
		return new Member(null, userId, passwordEncoder.encode(userPassword), "Member");
	}
	
}
