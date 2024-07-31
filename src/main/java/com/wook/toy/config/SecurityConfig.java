package com.wook.toy.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.wook.toy.security.handler.MemberAuthFailureHandler;
import com.wook.toy.security.handler.MemberAuthSuccessHandler;
import com.wook.toy.security.provider.MemberAuthenticatorProvider;
import com.wook.toy.security.service.MemberDetailsService;

import jakarta.servlet.DispatcherType;

@Configuration																	// 의존성 주입을 위해 직접 Bean을 생성하기 위해 사용
@EnableWebSecurity																// Spring Security를 활성화 하고 웹 보안 설정을 위해 사용(@Configuration과 같이 사용)_스프링 시큐리티가 스프링 필터체인에 등록 됨.
@EnableMethodSecurity															// 애플리케이션 내부의 설정한 권한에 맞는 사용자만 해당 메서드 사용을 위해 구성
public class SecurityConfig {													// Spring Security 에서 제공하는 인증,인가를 위한 필터들의 모음
	
	@Bean
	public PasswordEncoder passwordEncoder() {									// PasswordEncoder Bean 직접 생성
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	MemberAuthFailureHandler memberAuthFailureHandler;							// 로그인 시 사용자의 권한 확인 후 실패 시 사용되는 Handler
	
	@Autowired
	MemberAuthSuccessHandler memberAuthSuccessHandler;							// 로그인 시 사용자의 권한 확인 후 성공 시 사용되는 Handler
	
	@Autowired																	// 커스텀한 MemberAuthenticatorProvider를 주입
	MemberAuthenticatorProvider memberAuthenticatorProvider;					// 해당 클래스로 MemberDetailsService 내부 로직 수행 및 인증 처리
	
	@Autowired
	MemberDetailsService memberDetailsService;									// 로그인 기억하기 사용을 위해 MemberAuthenticatorProvider 내부 선언
	
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {		// Spring Security 6 버전 부터는 AuthenticationManagerBuilder를 직접 생성하여 AuthenticationManager를 생성해야 한다.
		auth.authenticationProvider(memberAuthenticatorProvider);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {	// 초기화 단계에서 HttpSecurity 객체가 실제 설정한 필터를 생성
		http.csrf()																	// Cross site Request forgery로 사이즈간 위조 요청인데, 즉 정상적인 사용자가 의도치 않은 위조요청을 보내는 것을 의미
			.disable();																// 서버에 인증정보를 저장하지 않기 때문에 굳이 불필요한 csrf 코드들을 작성할 필요가 없다.
		
		http.cors().disable();														// 교차 출처 리소스 공유(Cross-Origin Resource Sharing, CORS)는 브라우저가 자신의 출처가 아닌 다른 어떤 출처(도메인, 스킴 혹은 포트)로부터 자원을 로딩하는 것을 허용하도록 서버가 허가 해주는 HTTP 헤더 기반 메커니즘
		http.headers((headerConfig) ->
	            headerConfig.frameOptions(frameOptionsConfig ->
	                    frameOptionsConfig.disable()
	            )
		);
		
		http.authorizeHttpRequests(authorize -> {									// Http 요청에 대한 인가 설정을 하는 구역
			try {
				authorize
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()		// 스프링 시큐리티 6.0부터 forward 방식 페이지 이동에도 default로 인증이 걸리도록 변경되었음
					.requestMatchers("/css/**", "/images/**", "/js/**", "/framework/**", "/index", "/login/**", "/common/error").permitAll() // 앞 경로에 해당하는 리소스는 권한 체크 해제
					.requestMatchers("/admin/**").hasRole("ADMIN")					// "/admin/**" 경로에 해당하는 리소스는 'ADMIN' 권한만 허용
					.anyRequest()													// 모든 리소스를 의미하며 접근허용 리소스 및 인증 후 특정 레벨의 권한을 가진 사용자만 접근가능한 리소스를 설정하고 그 외 나머지 리소스들을 의미
					.authenticated()												// 무조건 인증 절차 필요
				.and()
					.formLogin()													// 로그인 페이지와 기타 로그인 처리 및 성공 실패 처리를 사용하겠다는 의미
					.loginPage("/login/loginView")									// Spring에서 제공하는 login페이지가 아니라 사용자가 커스텀한 로그인 페이지를 사용할때 사용
					.loginProcessingUrl("/login/loginProcess")						// 인증처리를 하는 URL 설정하며, "/login-process"가 호출되면 인증처리를 수행하는 필터 호출
					.usernameParameter("userId")									// 로그인을 하는데 username 파라미터 설정
					.passwordParameter("userPassword")								// 로그인을 하는데 password 파라미터 설정
					.defaultSuccessUrl("/index", true)								// 정상적으로 인증 성공 시 이동하는 페이지
					.failureHandler(memberAuthFailureHandler)						// 인증 실패 후 별도의 처리가 필요한 경우 커스텀 핸들러를 생성하여 등록
					.successHandler(memberAuthSuccessHandler)						// 정상 인증 성공 후 별도의 처리가 필요한 경우 커스텀 핸들러 생성하여 등록
					.permitAll()
				.and()
					.logout()														// 
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
