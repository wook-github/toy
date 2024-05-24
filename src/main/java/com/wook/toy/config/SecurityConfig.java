package com.wook.toy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.cors().disable()
			.headers((headerConfig) ->
		            headerConfig.frameOptions(frameOptionsConfig ->
		                    frameOptionsConfig.disable()
		            )
		    )
			.authorizeHttpRequests(request -> request
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers("/css/**", "/images/**", "/js/**", "/framework/**", "/index", "/login/**").permitAll()
				.anyRequest().authenticated()
				/*
				 * ) .exceptionHandling((exceptionConfig) -> exceptionConfig
				 * .authenticationEntryPoint(unauthorizedEntryPoint)
				 * .accessDeniedHandler(accessDeniedHandler)
				 */
		    )
			.formLogin(login -> login
					.loginPage("/login/loginView")
					.loginProcessingUrl("/login/loginProcess")
					.usernameParameter("userId")
					.passwordParameter("userPassword")
					.defaultSuccessUrl("/index", true)
					.permitAll()
			)
			.logout((logoutConfig) ->
		            logoutConfig.logoutSuccessUrl("/") 
		    );
		
		return http.build();
	}
	/*
	 * public final AuthenticationEntryPoint unauthorizedEntryPoint = (request,
	 * response, authException) -> { ErrorResponse fail = new
	 * ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
	 * response.setStatus(HttpStatus.UNAUTHORIZED.value()); String json = new
	 * ObjectMapper().writeValueAsString(fail);
	 * response.setContentType(MediaType.APPLICATION_JSON_VALUE); PrintWriter writer
	 * = response.getWriter(); writer.write(json); writer.flush(); };
	 * 
	 * public final AccessDeniedHandler accessDeniedHandler = (request, response,
	 * accessDeniedException) -> { ErrorResponse fail = new
	 * ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
	 * response.setStatus(HttpStatus.FORBIDDEN.value()); String json = new
	 * ObjectMapper().writeValueAsString(fail);
	 * response.setContentType(MediaType.APPLICATION_JSON_VALUE); PrintWriter writer
	 * = response.getWriter(); writer.write(json); writer.flush(); };
	 * 
	 * @Getter
	 * 
	 * @RequiredArgsConstructor public class ErrorResponse {
	 * 
	 * private final HttpStatus status; private final String message; }
	 */
}
