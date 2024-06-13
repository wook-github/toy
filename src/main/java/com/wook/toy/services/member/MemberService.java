package com.wook.toy.services.member;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;

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
	
}
