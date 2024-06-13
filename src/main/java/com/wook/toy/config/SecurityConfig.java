package com.wook.toy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.wook.toy.security.handler.MemberAuthFailureHander;
import com.wook.toy.security.provider.MemberAuthenticatorProvider;
import com.wook.toy.security.service.MemberDetailsService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 커스텀한 MemberAuthenticatorProvider를 주입
	// 해당 클래스로 MemberDetailsService 내부 로직 수행 및 인증 처리
	@Autowired
	MemberAuthenticatorProvider memberAuthenticatorProvider;
	
	// 로그인 기억하기 사용을 위해 MemberAuthenticatorProvider 내부 선언
	@Autowired
	MemberDetailsService memberDetailsService;
	
	// Spring Security 6 버전 부터는 AuthenticationManagerBuilder를 직접 생성하여 
	// AuthenticationManager를 생성해야 한다.
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(memberAuthenticatorProvider);
	}
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().disable();
		http.headers((headerConfig) ->
	            headerConfig.frameOptions(frameOptionsConfig ->
	                    frameOptionsConfig.disable()
	            )
		);
		
		http.authorizeHttpRequests(authorize -> {
			try {
				authorize
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.requestMatchers("/css/**", "/images/**", "/js/**", "/framework/**", "/index", "/login/**").permitAll()
					.requestMatchers("/admin/**")
					.hasRole("ADMIN")
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login/loginView")
					.loginProcessingUrl("/login/loginProcess")
					.usernameParameter("userId")
					.passwordParameter("userPassword")
					.defaultSuccessUrl("/index", true)
					.failureHandler(new MemberAuthFailureHander())
					.permitAll()
				.and()
					.logout()
					.logoutUrl("/login/logout")
					.logoutSuccessUrl("/index")
					.deleteCookies("JSESSIONID");
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		//http.rememberMe()
		//	.key("wook")
		//	.tokenValiditySeconds(60 * 60 * 24 * 7)
		//	.userDetailsService(memberDetailsService)
		//	.rememberMeParameter("remember-me");
		
		return http.build();
	}
}
