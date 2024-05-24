package com.wook.toy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class MainController {

	@GetMapping("/index")
	public ModelAndView index(@AuthenticationPrincipal User user, ModelAndView model) {
		if(user != null
			&& user.getUsername() != null && !"".equals(user.getUsername())
			&& user.getAuthorities() != null && !"".equals(user.getAuthorities())) {
			
			model.addObject("userId", user.getUsername());
			model.addObject("userRoles", user.getAuthorities());
		}
		
		model.setViewName("contents/index");
		return model;
	}
	
}
