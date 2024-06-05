package com.wook.toy.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.domain.Member;
import com.wook.toy.dto.JoinMemberDto;
import com.wook.toy.services.member.MemberService;
import com.wook.toy.services.member.RegisterMemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private final RegisterMemberService registerMemberService;
	private final MemberService memberService;
	
	public LoginController(RegisterMemberService registerMemberService, MemberService memberService) {
		this.registerMemberService = registerMemberService;
		this.memberService = memberService;
	}
	
	@GetMapping("/loginView")
	public ModelAndView loginPage(ModelAndView model) {
		model.addObject("menu", "로그인");
		model.setViewName("contents/login/loginView");
		return model;
	}
	
	@GetMapping("/joinView")
	public ModelAndView joinPage(ModelAndView model) {
		model.addObject("menu", "회원가입");
		model.setViewName("contents/login/joinView");
		return model;
	}
	
	@PostMapping("/joinUser")
	@ResponseBody
	public ResponseEntity<String> join(JoinMemberDto dto, HttpServletRequest request) {
		try {
			BigDecimal userNum = registerMemberService.joinUser(dto, request);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("userNum", userNum);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("userNum", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/checkId")
	@ResponseBody
	public ResponseEntity<String> checkId(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			
			if(param != null && !"".equals(param.get("userId"))) {
				String userId = (String) param.get("userId");
						
				Optional<Member> member = memberService.findOne(userId);
				if(member.isEmpty()) {
					rslt.put("userId", null);
				} else {
					rslt.put("userId", member.get().getUserId());
				}
			}
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("userId", null);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
}
