package com.wook.toy.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private RegisterMemberService registerMemberService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/loginView")
	public ModelAndView loginPage(ModelAndView model) {
		model.addObject("menu", "로그인");
		model.setViewName("contents/login/loginView");
		return model;
	}
	
	@GetMapping("/findIdView")
	public ModelAndView findIdView(ModelAndView model) {
		model.addObject("menu", "아이디 찾기");
		model.setViewName("contents/login/findIdView");
		return model;
	}
	
	@PostMapping("/findId")
	@ResponseBody
	public ResponseEntity<String> findId(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			
			if(param != null) {
				Member member = memberService.findByUser(param);
				
				if(member != null && !"".equals(member.getUserId())) {
					rslt.put("userId", member.getUserId());
				} else {
					rslt.put("userId", null);
				}
			}
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("userId", null);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/resetPwView")
	public ModelAndView resetPwView(ModelAndView model) {
		model.addObject("menu", "비밀번호 재설정");
		model.setViewName("contents/login/resetPwView");
		return model;
	}
	
	@PostMapping("/resetPw")
	@ResponseBody
	public ResponseEntity<String> resetPw(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			
			BigDecimal userNumber = memberService.resetPw(param);
			
			rslt.put("userNum", userNumber);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("userId", null);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
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
						
				Member member = memberService.findByUser(param);
				if(member != null && member.getUserNumber().compareTo(BigDecimal.ZERO) != 0) {
					rslt.put("userId", member.getUserId());
				} else {
					rslt.put("userId", null);
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
