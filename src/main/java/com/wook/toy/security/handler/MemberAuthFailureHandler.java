package com.wook.toy.security.handler;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MemberAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response
			, AuthenticationException exception) throws ServletException, IOException {
		
		
		String errorMsg = "";
		if(exception instanceof BadCredentialsException) {
			errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인 해주세요.";
		} else if(exception instanceof InternalAuthenticationServiceException) {
			errorMsg = "시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의 하세요.";
		} else if(exception instanceof UsernameNotFoundException) {
			errorMsg = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
		} else if(exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMsg = "인증 요청이 거부되었습니다. 관리자에게 문의 하세요.";
		} else {
			errorMsg = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의 하세요.";
		}
		
		HttpSession session = request.getSession(); 					// 메세지를 담기 위한 세션
		session.setAttribute("loginErrorMsg", errorMsg);				// 메세지 설정
		setDefaultFailureUrl("/login/loginView");						// 로그인 실패시 이동할 경로 설정
		super.onAuthenticationFailure(request, response, exception);
	}
}
