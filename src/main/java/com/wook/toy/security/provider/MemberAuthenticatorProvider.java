package com.wook.toy.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.wook.toy.domain.Member;
import com.wook.toy.security.service.MemberDetails;
import com.wook.toy.security.service.MemberDetailsService;

@Component
public class MemberAuthenticatorProvider implements AuthenticationProvider {
	
	@Autowired
	private MemberDetailsService memberDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String userPassword = (String) authentication.getCredentials();
		
		MemberDetails memberDetails = (MemberDetails) memberDetailsService.loadUserByUsername(userId);
		
		// 비밀번호 비교 Start
		String dbPassword = memberDetails.getPassword();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if(!passwordEncoder.matches(userPassword, dbPassword)) {
			throw new BadCredentialsException("[비밀번호 미일치] 아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		// 비밀번호 비교 End
		
		Member member = memberDetails.getMember();
		if(member == null || member.getUseYn() == null || "N".equals(member.getUseYn())) {
			throw new BadCredentialsException("[사용자 확인] 사용할 수 없는 계정입니다.");
		}
		
		return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
