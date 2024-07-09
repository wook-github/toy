package com.wook.toy.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.domain.Board;
import com.wook.toy.domain.Member;
import com.wook.toy.services.member.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/adminMain")
	public ModelAndView adminMainPage(ModelAndView model) {
		model.setViewName("contents/admin/adminMain");
		return model;
	}
	
	@GetMapping("/userManageList")
	public ModelAndView userManageList(@RequestParam HashMap<String, Object> param
			, @PageableDefault(page = 0, size = 10, sort = "userNumber", direction = Sort.Direction.DESC) Pageable pageable
			, ModelAndView model) {
		
		Page<Member> list = memberService.getMemberList(param, pageable);
		
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		int totalPage = list.getTotalPages();
		
		model.addObject("memberList", list);
		model.addObject("nowPage", nowPage);
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("totalPage", totalPage);
		
		model.setViewName("contents/admin/userManageList");
		return model;
	}
	
	@GetMapping("/userInfo")
	public ModelAndView userInfo(@RequestParam HashMap<String, Object> param, ModelAndView model) {
		
		Member member = null;
		if(param != null && param.get("userId") != null && !"".equals(param.get("userId"))) {
			member = memberService.findByUser(param);
		} else {
			member = member.createMember();
		}

		model.addObject("info", param);
		model.addObject("memberInfo", member);
		model.setViewName("contents/admin/userInfo");
		return model;
	}
	
	@GetMapping("/userManage")
	public ModelAndView userManage(@RequestParam HashMap<String, Object> param, ModelAndView model) {
		
		Member member = null;
		if(param != null && param.get("userId") != null && !"".equals(param.get("userId"))) {
			member = memberService.findByUser(param);
			model.addObject("pageMode", "U");
		} else {
			member = member.createMember();
			model.addObject("pageMode", "C");
		}
		
		model.addObject("info", param);
		model.addObject("memberInfo", member);
		model.setViewName("contents/admin/userManage");
		return model;
	}
	
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(Member member, HttpServletRequest request) {
		try {
			Boolean result = false;
			
			if(member != null && member.getUserNumber() != null && member.getUserNumber().compareTo(BigDecimal.ZERO) > 0) {
				result = memberService.modifyUser(member);
			}
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("result", result);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("result", false);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/deleteUser")
	public ResponseEntity<String> deleteUser(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			if(param != null && param.get("userId") != null && !"".equals(param.get("userId"))) {
				Boolean result = memberService.whdwlUser(param);
				
				HashMap<String, Object> rslt = new HashMap<String, Object>();
				rslt.put("result", result);
				
				return new ResponseEntity(rslt, HttpStatus.OK);
			} else {
				HashMap<String, Object> rslt = new HashMap<String, Object>();
				rslt.put("result", false);
				
				return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("result", false);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
}
