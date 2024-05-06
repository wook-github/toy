package com.wook.toy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

	@GetMapping("/index")
	public String index(@AuthenticationPrincipal User user, Model model) {
		if(user != null
			&& user.getUsername() != null && !"".equals(user.getUsername())
			&& user.getAuthorities() != null && !"".equals(user.getAuthorities())) {
			
			model.addAttribute("userId", user.getUsername());
			model.addAttribute("userRoles", user.getAuthorities());
		}
		return "index";
	}
	
}
