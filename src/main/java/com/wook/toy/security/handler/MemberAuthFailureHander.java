package com.wook.toy.security.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MemberAuthFailureHander extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws ServletException, IOException {
		HttpSession session = request.getSession(); 					// 메세지를 담기 위한 세션
		session.setAttribute("loginErrorMsg", "계정이 존재하지 않습니다.");	// 메세지 설정
		setDefaultFailureUrl("/login/loginView");						// 로그인 실패시 이동할 경로 설정
		super.onAuthenticationFailure(request, response, exception);
	}
}
