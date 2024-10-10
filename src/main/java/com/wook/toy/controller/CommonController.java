package com.wook.toy.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wook.toy.services.common.CommonService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CommonController {

	@Autowired
	CommonService commonService;
	
	@GetMapping(value = "/common/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam HashMap<String,Object> param
			, HttpServletRequest request
			, HttpServletResponse response ) throws Exception {
	
		return commonService.downloadFile(param, request, response);
	}
}
