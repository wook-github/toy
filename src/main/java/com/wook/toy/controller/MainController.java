package com.wook.toy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.security.service.MemberDetails;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/")
public class MainController {

	@GetMapping("index")
	public ModelAndView index(@AuthenticationPrincipal MemberDetails memberDetails, HttpServletRequest request, ModelAndView model) {
		if(memberDetails != null
			&& memberDetails.getUsername() != null && !"".equals(memberDetails.getUsername())
			&& memberDetails.getAuthorities() != null && !memberDetails.getAuthorities().isEmpty()) {
			
			HttpSession session = request.getSession();
			session.setAttribute("user", memberDetails.getMember());
		}
		
		model.setViewName("contents/index");
		return model;
	}
	
}
