package com.wook.toy.services.member;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;

@Service
public class MemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository repository;
	
	public MemberService(PasswordEncoder passwordEncoder, MemberRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}
	
	public Optional<Member> findOne(String userId) {
		return repository.findByUserId(userId);
	}
	
	public Member findByUser(HashMap<String, Object> param) {
		
		if(param.get("userId") != null && !"".equals(param.get("userId"))) {
			String userId = (String) param.get("userId");
			 
			Optional<Member> member = repository.findByUserId(userId);
			
			return member.get();
		} else {
			String userName = param.get("userName").toString();
			String userBirth = param.get("userBirth").toString().replaceAll("[-]", "");
			String userPhone = param.get("userPhone").toString().replaceAll("[-]", "");
			
			return repository.findByUserNameAndUserBirthAndUserPhone(userName, userBirth, userPhone);			
		}
		
	}
	
	public BigDecimal resetPw(HashMap<String, Object> param) {
		Member member = this.findByUser(param);
		
		member.setUserId((String) param.get("userId"));
		member.setUserPassword(passwordEncoder.encode(member.getUserPassword()));
		
		repository.save(member);
		
		return member.getUserNumber();
	}
	
}
