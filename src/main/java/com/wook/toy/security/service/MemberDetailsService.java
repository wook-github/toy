package com.wook.toy.security.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;

@Component
public class MemberDetailsService implements UserDetailsService {

	@Autowired
	private final MemberRepository memberRepository;
	
	public MemberDetailsService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		Member member = memberRepository.findByUserId(userId);
		
		if(member == null) {
			throw new UsernameNotFoundException(userId + "을(를) 찾을 수 없습니다.");
		}
		if(!"Y".equals(member.getUseYn())) {
			throw new UsernameNotFoundException("사용할 수 없는 계정입니다.");
		}
		
		return new MemberDetails(member); 
	}
	
	public void updateLastLoginDt(String userId) {
		if(userId != null && !"".equals(userId)) {
			Member member = memberRepository.findByUserId(userId);
			
			Date now = new Date();
			SimpleDateFormat formatDt = new SimpleDateFormat("yyyyMMddHHmmss");
			member.setLastLoginDt(formatDt.format(now));
			
			memberRepository.save(member);
		}
	}
}
