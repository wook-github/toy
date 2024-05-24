package com.wook.toy.services.member;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.dto.JoinMemberDto;
import com.wook.toy.repository.MemberRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RegisterMemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository repository;
	
	public RegisterMemberService(PasswordEncoder passwordEncoder, MemberRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}
	
	public BigDecimal joinUser(JoinMemberDto dto, HttpServletRequest request) {
		Member member = dto.toEntity(passwordEncoder, request);
		
		this.validateDuplicateMember(dto.getUserId());
		repository.save(member);
		
		return member.getUserNumber();
	}
	
	private void validateDuplicateMember(String userId) {
		repository.findByUserId(userId)
			.ifPresent(m -> {
				throw new IllegalStateException("가입 이력이 있는 회원입니다.");
			});
	}
}
