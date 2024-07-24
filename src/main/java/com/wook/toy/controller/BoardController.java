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
import com.wook.toy.domain.Comment;
import com.wook.toy.services.board.BoardService;
import com.wook.toy.services.comment.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/noticeList")
	public ModelAndView noticeList(@RequestParam HashMap<String, Object> param
			, @PageableDefault(page = 0, size = 10, sort = "boardNumber", direction = Sort.Direction.DESC) Pageable pageable
			, ModelAndView model) {
		param.put("boardSection", "01");
		param.put("useYn", "Y");
		
		Page<Board> list = boardService.getBoardList(param, pageable);
		
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		int totalPage = list.getTotalPages();
		
		model.addObject("boardList", list);
		model.addObject("nowPage", nowPage);
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("totalPage", totalPage);
		
		model.addObject("menu", "공지사항");
		model.setViewName("contents/board/noticeList");
		return model;
	}
	
	@GetMapping("/noticeInfo")
	public ModelAndView noticeInfo(@RequestParam HashMap<String, Object> param
			, HttpServletRequest request
			, HttpServletResponse response
			, ModelAndView model) {
		
		if(param != null && param.get("boardNumber") != null && !"".equals(param.get("boardNumber"))) {
			BigDecimal boardNumber = new BigDecimal((String) param.get("boardNumber")); 
			model.addObject("boardInfo", boardService.getBoardInfo(boardNumber, request, response));
		} else {
			model.addObject("boardInfo", new Board());
		}
		
		model.addObject("info", param);
		model.addObject("menu", "공지사항");
		model.setViewName("contents/board/noticeInfo");
		return model;
	}
	
	@GetMapping("/boardList")
	public ModelAndView boardList(@RequestParam HashMap<String, Object> param
			, @PageableDefault(page = 0, size = 10, sort = "boardNumber", direction = Sort.Direction.DESC) Pageable pageable
			, ModelAndView model) {
		param.put("boardSection", "02");
		param.put("useYn", "Y");
		
		Page<Board> list = boardService.getBoardList(param, pageable);
		
		int nowPage = list.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		int totalPage = list.getTotalPages();
		
		model.addObject("boardList", list);
		model.addObject("nowPage", nowPage);
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("totalPage", totalPage);
		
		model.addObject("menu", "동네 소식");
		model.addObject("param", param);
		model.setViewName("contents/board/boardList");
		return model;
	}
	
	@GetMapping("/boardInfo")
	public ModelAndView boardInfo(@RequestParam HashMap<String, Object> param
			, @PageableDefault(page = 0, size = 10, sort = "commentNumber", direction = Sort.Direction.DESC) Pageable pageable
			, HttpServletRequest request
			, HttpServletResponse response
			, ModelAndView model) {
		
		if(param != null && param.get("boardNumber") != null && !"".equals(param.get("boardNumber"))) {
			BigDecimal boardNumber = new BigDecimal((String) param.get("boardNumber")); 
			model.addObject("boardInfo", boardService.getBoardInfo(boardNumber, request, response));
			
			param.put("useYn", "Y");
			model.addObject("commentList", commentService.getCommentList(param, pageable));
		} else {
			model.addObject("boardInfo", new Board());
		}
		
		model.addObject("info", param);
		model.addObject("menu", "동네 소식");
		model.setViewName("contents/board/boardInfo");
		return model;
	}
	
	@GetMapping("/boardManage")
	public ModelAndView boardManage(@RequestParam HashMap<String, Object> param
			, HttpServletRequest request
			, HttpServletResponse response
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
		model.addObject("menu", "동네 소식");
		model.setViewName("contents/board/boardManage");
		return model;
	}
	
	@PostMapping("/insertBoard")
	public ResponseEntity<String> insertBoard(Board board, HttpServletRequest request) {
		try {
			board.setBoardSection("02");
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
			board.setBoardSection("02");
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
	
	@PostMapping("/insertComment")
	public ResponseEntity<String> insertComment(Comment comment, HttpServletRequest request) {
		try {
			BigDecimal commentNumber = commentService.insertToUpdateComment(comment);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("commentNumber", commentNumber);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("commentNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/updateComment")
	public ResponseEntity<String> updateComment(Comment comment, HttpServletRequest request) {
		try {
			BigDecimal commentNumber = commentService.insertToUpdateComment(comment);
			
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("commentNumber", commentNumber);
			
			return new ResponseEntity(rslt, HttpStatus.OK);
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("commentNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/deleteComment")
	public ResponseEntity<String> deleteComment(@RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		try {
			if(param != null && !"".equals(param.get("commentNumber"))) {
				BigDecimal commentNumber = new BigDecimal((String) param.get("commentNumber"));
				
				BigDecimal result = commentService.deleteComment(commentNumber);
				
				HashMap<String, Object> rslt = new HashMap<String, Object>();
				rslt.put("commentNumber", result);
				
				return new ResponseEntity(rslt, HttpStatus.OK);
			} else {
				HashMap<String, Object> rslt = new HashMap<String, Object>();
				rslt.put("commentNumber", 0);
				
				return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			HashMap<String, Object> rslt = new HashMap<String, Object>();
			rslt.put("boardNumber", 0);
			
			return new ResponseEntity(rslt, HttpStatus.BAD_REQUEST);
		}
	}
}
