package com.wook.toy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.dto.JoinMemberDto;
import com.wook.toy.services.member.RegisterMemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private final RegisterMemberService registerMemberService;
	
	public LoginController(RegisterMemberService registerMemberService) {
		this.registerMemberService = registerMemberService;
	}
	
	@GetMapping("/loginView")
	public ModelAndView loginPage(ModelAndView model) {
		model.setViewName("contents/login/loginView");
		return model;
	}
	
	@GetMapping("/joinView")
	public ModelAndView joinPage(ModelAndView model) {
		model.setViewName("contents/login/joinView");
		return model;
	}
	
	@PostMapping("/joinUser")
	@ResponseBody
	public ResponseEntity<String> join(@RequestBody JoinMemberDto dto, HttpServletRequest request) {
		try {
			registerMemberService.joinUser(dto, request);
			return ResponseEntity.ok("join success");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
