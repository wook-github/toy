package com.wook.toy.services.member;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.dto.JoinMemberDto;
import com.wook.toy.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RegisterMemberService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberRepository repository;
	
	public BigDecimal joinUser(JoinMemberDto dto, HttpServletRequest request) {
		Member member = dto.toEntity(passwordEncoder, request);
		
		this.validateDuplicateMember(dto.getUserId());
		repository.save(member);
		
		return member.getUserNumber();
	}
	
	private void validateDuplicateMember(String userId) {
		Member member = repository.findByUserId(userId);
		
		if(member != null && member.getUserId() != null && !"".equals(member.getUserId())) {
			throw new IllegalStateException("가입 이력이 있는 회원입니다.");
		}
	}
}
