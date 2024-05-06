package com.wook.toy.services.member;

import java.math.BigDecimal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
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
	
	public BigDecimal joinUser(String userId, String userPassword, String userName, HttpServletRequest request) {
		Member member = Member.createMember(userId, userPassword, userName, passwordEncoder, request);
		
		this.validateDuplicateMember(member);
		repository.save(member);
		
		return member.getUserNumber();
	}
	
	private void validateDuplicateMember(Member member) {
		repository.findByUserId(member.getUserId())
			.ifPresent(m -> {
				throw new IllegalStateException("가입 이력이 있는 회원입니다.");
			});
	}
}
