package com.wook.toy.services.member;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;

@Service
public class MemberService {

	private final MemberRepository repository;
	
	public MemberService(MemberRepository repository) {
		this.repository = repository;
	}
	
	public Optional<Member> findOne(String userId) {
		return repository.findByUserId(userId);
	}
	
}
