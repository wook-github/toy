package com.wook.toy.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.wook.toy.domain.Member;
import com.wook.toy.utility.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinMemberDto {

	private String userId;
	private String userPassword;
	private String userName;
	private String userNickname;
	private String userBirth;
	private String userPhone;
	private String userEmail;
	
	@Builder
	public JoinMemberDto(String userId, String userPassword, String userName, String userNickname, String userBirth, String userPhone, String userEmail) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userNickname = userNickname;
		this.userBirth = userBirth;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
	}
	
	public Member toEntity(PasswordEncoder passwordEncoder, HttpServletRequest request) {
		Member member = Member.createMember();
		
		Date now = new Date();
		SimpleDateFormat formatDt = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat formatYmd = new SimpleDateFormat("yyyyMMdd");
		
		member.setUserId(userId);
		member.setUserPassword(passwordEncoder.encode(userPassword));
		member.setUserName(userName);
		member.setUserNickname(userNickname);
		member.setUserBirth(userBirth.replaceAll("[-]", ""));
		member.setUserPhone(userPhone.replaceAll("[-]", ""));
		member.setUserEmail(userEmail);
		member.setUserRole("ROLE_MEMBER");
		member.setJoinDt(formatDt.format(now));
		member.setUseYn("Y");
		member.setUpdtYmd(formatYmd.format(now));
		member.setUpdusrId("ADMIN");
		member.setUpdusrIp(CommonUtil.getClientIp(request));
		
		return member;
	}
	
}
