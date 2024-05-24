package com.wook.toy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/member")
public class MemberController {

	@GetMapping("/main")
	public ModelAndView memberMainPage(ModelAndView model) {
		model.setViewName("contents/user/userMain");
		return model;
	}
}
