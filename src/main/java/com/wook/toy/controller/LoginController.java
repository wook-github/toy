package com.wook.toy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wook.toy.dto.JoinMemberDto;
import com.wook.toy.services.member.RegisterMemberService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private final RegisterMemberService registerUserService;
	
	public LoginController(RegisterMemberService registerUserService) {
		this.registerUserService = registerUserService;
	}
	
	@GetMapping("/loginView")
	public String loginPage() {
		return "login/loginView";
	}
	
	@GetMapping("/joinView")
	public String joinPage() {
		return "login/joinView";
	}
	
	@PostMapping("/join")
	public ResponseEntity<String> join(@RequestBody JoinMemberDto dto) {
		try {
			registerUserService.joinUser(dto.getUserId(), dto.getUserPassword());
			return ResponseEntity.ok("join success");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}