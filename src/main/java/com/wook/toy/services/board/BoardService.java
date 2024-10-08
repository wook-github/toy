package com.wook.toy.services.board;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wook.toy.domain.Board;
import com.wook.toy.repository.BoardRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class BoardService {

	@Autowired
	private BoardRepository repository;
	
	public Page<Board> getBoardList(HashMap<String, Object> param, Pageable pageable) {
		if(param != null && (param.get("boardSection") != null && !"".equals(param.get("boardSection")))) {
			String boardSection = (String) param.get("boardSection");
			
			if(param.get("searchKeyword") != null && !"".equals(param.get("searchKeyword"))) {
				String searchKeyword = (String) param.get("searchKeyword");
				return repository.findByBoardSectionAndUseYnAndBoardTitleContaining(boardSection, "Y", searchKeyword, pageable);
			} else {
				if(param.get("useYn") != null) {
					String useYn = (String) param.get("useYn") != "Y" ? "N" : "Y";
					return repository.findByBoardSectionAndUseYn(boardSection, useYn, pageable);
				} else {
					return repository.findByBoardSection(boardSection, pageable);
				}
				
			}
		} else {
			System.out.println("검색 조회 실패");
			return Page.empty();
		}
		
	}
	
	public Board getBoardInfo(BigDecimal boardNumber) {
		Board boardInfo = repository.findByBoardNumber(boardNumber);
		if(boardInfo != null && boardInfo.getBoardNumber() != BigDecimal.ZERO) {
			return boardInfo;
		} else {
			return new Board();
		}
	}
	
	public Board getBoardInfo(BigDecimal boardNumber, HttpServletRequest request, HttpServletResponse response) {
		Board boardInfo = repository.findByBoardNumber(boardNumber);
		if(boardInfo != null && boardInfo.getBoardNumber() != BigDecimal.ZERO) {
			
			Cookie[] cookies = request.getCookies();
			
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					
					if(cookie.getName() != null && !"".equals(cookie.getName()) && "view_cookie".equals(cookie.getName())) {
						if(!cookie.getValue().contains(boardNumber.toString())) {
							cookie.setValue(cookie.getValue() + "_[" + boardNumber.toString() + "]");
							cookie.setMaxAge(60 * 60 * 2);  /* 쿠키 시간 */
							response.addCookie(cookie);
							
							boardInfo.setBoardViewCnt(boardInfo.getBoardViewCnt().add(BigDecimal.ONE));
							boardInfo = repository.save(boardInfo);
						}
						break;
					} else {
						Cookie newCookie = new Cookie("view_cookie", "[" + boardNumber.toString() + "]");
						newCookie.setMaxAge(60 * 60 * 2);
						response.addCookie(newCookie);
						
						boardInfo.setBoardViewCnt(boardInfo.getBoardViewCnt().add(BigDecimal.ONE));
						boardInfo = repository.save(boardInfo);
					}
				}
			}
			
			return boardInfo;
		} else {
			return new Board();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal insertToUpdateBoard(Board board) {
		try {
			Board newBoard = new Board();
			
			if(board != null && board.getBoardNumber() != null && board.getBoardNumber().compareTo(BigDecimal.ZERO) > 0) {
				Board oldBoard = repository.findByBoardNumber(board.getBoardNumber());
				oldBoard.setBoardTitle(board.getBoardTitle());
				oldBoard.setBoardContents(board.getBoardContents());
				if(board.getFileNumber() != null && board.getFileNumber().compareTo(BigDecimal.ZERO) > 0) {
					oldBoard.setFileNumber(board.getFileNumber());
				}
				
				newBoard = repository.save(oldBoard);
			} else {
				newBoard = repository.save(board);
			}
			
			if(board.getBoardNumber().compareTo(BigDecimal.ZERO) > 0) {
				return newBoard.getBoardNumber();
			} else {
				return new BigDecimal(0);
			}
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			return new BigDecimal(0);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal deleteBoard(BigDecimal boardNumber) {
		try {
			Board board = repository.findByBoardNumber(boardNumber);
			board.setUseYn("N");
			
			Board newBoard = repository.save(board);
			if(newBoard != null) {
				return newBoard.getBoardNumber();
			} else {
				return new BigDecimal(0);
			}
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			return new BigDecimal(0);
		}
		
	}
}
