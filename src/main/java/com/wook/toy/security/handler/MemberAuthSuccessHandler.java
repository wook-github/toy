package com.wook.toy.security.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.wook.toy.security.service.MemberDetails;
import com.wook.toy.security.service.MemberDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MemberAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {
	
	@Autowired
	private MemberDetailsService memberDetailsService;
	
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// 로그인 실패 세션 삭제
		HttpSession session = request.getSession(false);
		if(session==null) return;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
		// 사용자 정보 내의 마지막 로그인 일시 수정
		MemberDetails member = (MemberDetails) authentication.getPrincipal();
		String userId = member.getUsername();
		memberDetailsService.updateLastLoginDt(userId);
		
		// Security가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보를 저장한 객체
        redirectStrategy.sendRedirect(request, response, "/index");	
	}
}
