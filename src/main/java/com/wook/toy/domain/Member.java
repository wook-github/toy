package com.wook.toy.domain;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wook.toy.utility.CommonUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="t_user", schema="toy", catalog = "toy")
@DynamicUpdate
@SequenceGenerator(name = "Member_Seq_Generator", sequenceName = "toy.t_user_seq", initialValue = 1, allocationSize = 1)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Member_Seq_Generator")
	private BigDecimal userNumber;
	
	private String userId;
	private String userPassword;
	private String userName;
	private String userNickname;
	private String userPhone;
	private String userEmail;
	private String userRole;
	
	@ColumnDefault("to_char(now(), 'YYYYMMDD24HHMISS')")
	private String joinDt;
	
	@ColumnDefault("0")
	private BigDecimal boardLikeCnt;
	
	@ColumnDefault("0")
	private BigDecimal commentLikeCnt;
	
	@ColumnDefault("'Y'")
	private String useYn;
	
	@ColumnDefault("to_char(now(), 'YYYYMMDD')")
	private String updtYmd;
	
	@ColumnDefault("'ADMIN'")
	private String updusrId;
	
	@ColumnDefault("'127.0.0.1'")
	private String updusrIp;
	private String rmks;
	
	public static Member createMember(String userId, String userPassword, String userName, PasswordEncoder passwordEncoder, HttpServletRequest request) {
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPassword(passwordEncoder.encode(userPassword));
		member.setUserName(userName);
		member.setUserRole("MEMBER");
		
		Date now = new Date();
		SimpleDateFormat formatDt = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat formatYmd = new SimpleDateFormat("yyyyMMdd");
		
		member.setJoinDt(formatDt.format(now));
		member.setUseYn("Y");
		member.setUpdtYmd(formatYmd.format(now));
		member.setUpdusrId("ADMIN");
		member.setUpdusrIp(CommonUtil.getClientIp(request));
		
		return member;
	}
	
}
