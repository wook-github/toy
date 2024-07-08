package com.wook.toy.services.member;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;
import com.wook.toy.utility.CalendarUtil;
import com.wook.toy.utility.StringUtil;

@Service
public class MemberService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final MemberRepository repository;
	
	public MemberService(BCryptPasswordEncoder passwordEncoder, MemberRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}
	
	public Member findByUser(HashMap<String, Object> param) {
		
		if(param.get("userId") != null && !"".equals(param.get("userId"))) {
			String userId = (String) param.get("userId");
			
			return repository.findByUserId(userId);
		} else {
			String userName = param.get("userName").toString();
			String userBirth = param.get("userBirth").toString().replaceAll("[-]", "");
			String userPhone = param.get("userPhone").toString().replaceAll("[-]", "");
			
			return repository.findByUserNameAndUserBirthAndUserPhone(userName, userBirth, userPhone);			
		}
	}
	
	public BigDecimal resetPw(HashMap<String, Object> param) {
		String userId = (String) param.get("userId");
		String userPassword = (String) param.get("userPassword");
		
		Member member = this.findByUser(param);
		
		member.setUserId(userId);
		member.setUserPassword(passwordEncoder.encode(userPassword));
		
		repository.save(member);
		
		return member.getUserNumber();
	}
	
	public Member checkPw(HashMap<String, Object> param) {
		Member member = this.findByUser(param);
		
		if(param.get("userPassword") != null && !"".equals(param.get("userPassword"))) {
			String inpUserPassword = (String) param.get("userPassword");
			if(passwordEncoder.matches(inpUserPassword, member.getUserPassword())) {
				return member;
			} else {
				return member.createMember();
			}
		} else {
			return member.createMember();
		}
		
	}
	
	public Boolean modifyUser(Member member) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("userId", member.getUserId());
		
		Member memberInfo = this.findByUser(param);
		
		if(member.getUserPassword() != null && !"".equals(member.getUserPassword())) {
			memberInfo.setUserPassword(passwordEncoder.encode(member.getUserPassword()));
		}
		
		if(member.getUserNickname() != null && !"".equals(member.getUserNickname())) {
			memberInfo.setUserNickname(member.getUserNickname());
		}
		
		memberInfo.setUserPhone(member.getUserPhone().replaceAll("[-]", ""));
		memberInfo.setUserBirth(member.getUserBirth().replaceAll("[-]", ""));

		member = repository.save(memberInfo);
		if(member.getUserNumber() != null && member.getUserNumber().compareTo(BigDecimal.ZERO) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean whdwlUser(HashMap<String, Object> param) {
		Member member = this.findByUser(param);
		
		member.setUseYn("N");
		
		member = repository.save(member);
		if(member.getUserNumber() != null && member.getUserNumber().compareTo(BigDecimal.ZERO) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
}
