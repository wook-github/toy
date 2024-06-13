package com.wook.toy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.security.service.MemberDetails;


@RestController
public class MainController {

	@GetMapping("/index")
	public ModelAndView index(@AuthenticationPrincipal MemberDetails memberDetails, ModelAndView model) {
		if(memberDetails != null
			&& memberDetails.getUsername() != null && !"".equals(memberDetails.getUsername())
			&& memberDetails.getAuthorities() != null && !memberDetails.getAuthorities().isEmpty()) {
			
			model.addObject("userId", memberDetails.getUsername());
			model.addObject("userRoles", memberDetails.getAuthorities());
		}
		
		model.setViewName("contents/index");
		return model;
	}
	
}
