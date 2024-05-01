package com.wook.toy.services.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;

@Service
public class RegisterMemberService {

	private final PasswordEncoder passwordEncoder;
	private final MemberRepository repository;
	
	public RegisterMemberService(PasswordEncoder passwordEncoder, MemberRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}
	
	public Long joinUser(String userId, String userPassword) {
		Member member = Member.createMember(userId, userPassword, passwordEncoder);
		
		this.validateDuplicateMember(member);
		repository.save(member);
		
		return member.getUserNo();
	}
	
	private void validateDuplicateMember(Member member) {
		repository.findByUserId(member.getUserId())
			.ifPresent(m -> {
				throw new IllegalStateException("가입 이력이 있는 회원입니다.");
			});
	}
}
