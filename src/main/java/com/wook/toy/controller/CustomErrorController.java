package com.wook.toy.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CustomErrorController implements ErrorController {

	@ExceptionHandler(Throwable.class)
	@GetMapping("/error")
	public ModelAndView handleError(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		int statusCode = Integer.parseInt(status.toString());
		response.setStatus(statusCode);
		
		model.addObject("code", status.toString());
		model.addObject("msg", HttpStatus.valueOf(Integer.valueOf(status.toString())));
		model.setViewName("contents/common/error");
		
		return model;
	}
	
}
