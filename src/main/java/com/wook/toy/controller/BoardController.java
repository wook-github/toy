package com.wook.toy.controller;

import java.math.BigDecimal;
import java.util.HashMap;

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
import com.wook.toy.services.board.BoardService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/noticeList")
	public ModelAndView noticeList(@RequestParam HashMap<String, Object> param
			, @PageableDefault(page = 0, size = 10, sort = "boardNumber", direction = Sort.Direction.DESC) Pageable pageable
			, ModelAndView model) {
		param.put("boardSection", "01");
		param.put("useYn", "Y");
		model.addObject("boardList", boardService.getBoardList(param, pageable));
		
		model.addObject("menu", "공지사항");
		model.setViewName("contents/board/noticeList");
		return model;
	}
	
	@GetMapping("/noticeInfo")
	public ModelAndView noticeInfo(@RequestParam HashMap<String, Object> param
			, ModelAndView model) {
		
		if(param != null && param.get("boardNumber") != null && !"".equals(param.get("boardNumber"))) {
			BigDecimal boardNumber = new BigDecimal((String) param.get("boardNumber")); 
			model.addObject("boardInfo", boardService.getBoardInfo(boardNumber));
		} else {
			model.addObject("boardInfo", new Board());
		}
		
		model.addObject("info", param);
		model.addObject("menu", "공지사항");
		model.setViewName("contents/board/noticeInfo");
		return model;
	}
	
	@GetMapping("/noticeManage")
	public ModelAndView noticeManage(@RequestParam HashMap<String, Object> param
			, ModelAndView model) {
		
		if(param != null && param.get("boardNumber") != null && !"".equals(param.get("boardNumber"))) {
			BigDecimal boardNumber = new BigDecimal((String) param.get("boardNumber")); 
			model.addObject("boardInfo", boardService.getBoardInfo(boardNumber));
			param.put("pageMode", "U");
		} else {
			model.addObject("boardInfo", new Board());
			param.put("pageMode", "C");
		}
		
		model.addObject("info", param);
		model.addObject("menu", "공지사항");
		model.setViewName("contents/board/noticeManage");
		return model;
	}
	
	@PostMapping("/insertBoard")
	public ResponseEntity<String> insertBoard(Board board, HttpServletRequest request) {
		try {
			board.setBoardSection("01");
			board.setWriterId(SecurityContextHolder.getContext().getAuthentication().getName());
			
			BigDecimal boardNumber = boardService.insertToUpdateBoard(board);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", boardNumber);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/updateBoard")
	public ResponseEntity<String> updateBoard(Board board, HttpServletRequest request) {
		try {
			board.setBoardSection("01");
			board.setWriterId(SecurityContextHolder.getContext().getAuthentication().getName());
			
			BigDecimal boardNumber = boardService.insertToUpdateBoard(board);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", boardNumber);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/deleteBoard")
	public ResponseEntity<String> deleteBoard(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			if(param != null && !"".equals(param.get("boardNumber"))) {
				BigDecimal boardNumber = new BigDecimal((String) param.get("boardNumber"));
				
				BigDecimal result = boardService.deleteBoard(boardNumber);
				
				HashMap<String, Object> rslt = new HashMap<String, Object>();
				rslt.put("boardNumber", result);
				
				return new ResponseEntity(rslt, HttpStatus.OK);
			} else {
				HashMap<String, Object> rslt = new HashMap<String, Object>();
				rslt.put("boardNumber", 0);
				
				return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
}
