package com.wook.toy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wook.toy.services.board.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/noticeView")
	public ModelAndView noticeView(ModelAndView model) {
		model.addObject("menu", "공지사항");
		model.setViewName("contents/board/noticeView");
		
		return model;
	}
}
