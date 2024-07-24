package com.wook.toy.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.domain.Member;
import com.wook.toy.security.service.MemberDetails;
import com.wook.toy.services.member.MemberService;
import com.wook.toy.utility.CalendarUtil;
import com.wook.toy.utility.StringUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/userMain")
	public ModelAndView userMainPage(@AuthenticationPrincipal MemberDetails memberDetails, HttpServletRequest request, ModelAndView model) {
		if(memberDetails != null
			&& memberDetails.getUsername() != null && !"".equals(memberDetails.getUsername())
			&& memberDetails.getAuthorities() != null && !memberDetails.getAuthorities().isEmpty()) {
			
			HttpSession session = request.getSession();
			session.setAttribute("user", memberDetails.getMember());
			
			model.addObject("menu", "마이페이지");
			model.setViewName("contents/user/userMain");
		} else {
			model.setViewName("redirect:/login/loginView");
		}
		
		return model;
	}
	
	@GetMapping("/checkPwView")
	public ModelAndView checkPwView(@AuthenticationPrincipal MemberDetails memberDetails, @RequestParam HashMap<String, Object> param, ModelAndView model) {
		if(memberDetails != null
				&& memberDetails.getUsername() != null && !"".equals(memberDetails.getUsername())
				&& memberDetails.getAuthorities() != null && !memberDetails.getAuthorities().isEmpty()) {
			
			model.addObject("menu", "비밀번호 확인");
			model.addObject("info", param);
			model.setViewName("contents/user/checkPwView");
		} else {
			model.setViewName("redirect:/login/loginView");
		}
		
		return model;
	}
	
	@PostMapping("/checkPw")
	public ResponseEntity<String> checkPw(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			Member member = memberService.checkPw(param);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("userId", member.getUserId());
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/modifyUserView")
	public ModelAndView modifyUserView(@AuthenticationPrincipal MemberDetails memberDetails, ModelAndView model) {
		if(memberDetails != null
				&& memberDetails.getUsername() != null && !"".equals(memberDetails.getUsername())
				&& memberDetails.getAuthorities() != null && !memberDetails.getAuthorities().isEmpty()) {
			
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userId", memberDetails.getUsername());
			
			Member member = memberService.findByUser(param);
			member.setUserBirth(CalendarUtil.dateToString(member.getUserBirth()));
			member.setUserPhone(StringUtil.getPhoneFormat(member.getUserPhone()));
			
			model.addObject("menu", "회원정보 수정");
			model.addObject("member", member);
			model.setViewName("contents/user/modifyUserView");
		} else {
			model.setViewName("redirect:/login/loginView");
		}
		
		return model;
	}
	
	@PostMapping("/modifyUser")
	public ResponseEntity<String> modifyUser(Member member, HttpServletRequest request) {
		try {
			Boolean modifyYn = memberService.modifyUser(member);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("modifyYn", modifyYn);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/whdwlUserView")
	public ModelAndView whdwlUserView(@AuthenticationPrincipal MemberDetails memberDetails, ModelAndView model) {
		if(memberDetails != null
				&& memberDetails.getUsername() != null && !"".equals(memberDetails.getUsername())
				&& memberDetails.getAuthorities() != null && !memberDetails.getAuthorities().isEmpty()) {
			
			model.addObject("menu", "회원탈퇴");
			model.setViewName("contents/user/whdwlUserView");
		} else {
			model.setViewName("redirect:/login/loginView");
		}
		
		return model;
	}

	@PostMapping("/whdwlUser")
	public ResponseEntity<String> whdwlUser(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			Boolean whdwlYn = memberService.whdwlUser(param);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("whdwlYn", whdwlYn);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
}
