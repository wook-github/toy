package com.wook.toy.config;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wook.toy.domain.Member;
import com.wook.toy.services.member.MemberService;

@Component
public class MyMemberDetailService implements UserDetailsService {

	private final MemberService userService;
	
	public MyMemberDetailService(MemberService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Optional<Member> findOne = userService.findOne(userId);
		Member member = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 계정입니다."));
		
		return User.builder()
				.username(member.getUserId())
                .password(member.getUserPassword())
                .roles(member.getUserRole())
                .build();
	}
}
