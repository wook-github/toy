package com.wook.toy.services.board;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wook.toy.domain.Board;
import com.wook.toy.repository.BoardRepository;

@Service
public class BoardService {

	private final BoardRepository repository;
	
	public BoardService(BoardRepository repository) {
		this.repository = repository;
	}
	
	public Page<Board> getBoardList(HashMap<String, Object> param, Pageable pageable) {
		
		if(param != null && (param.get("boardSection") != null && !"".equals(param.get("boardSection")))) {
			String boardSection = (String) param.get("boardSection");
			String useYn = (String) param.get("useYn");
			
			if(param.get("searchKeyword") != null && !"".equals(param.get("searchKeyword"))) {
				String searchKeyword = (String) param.get("searchKeyword");
				return repository.findByBoardSectionAndUseYnAndBoardTitleContaining(boardSection, useYn, searchKeyword, pageable);
			} else {
				return repository.findByBoardSectionAndUseYn(boardSection, useYn, pageable);
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
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal insertToUpdateBoard(Board board) {
		try {
			Board newBoard = new Board();
			
			if(board != null && board.getBoardNumber() != null && board.getBoardNumber().compareTo(BigDecimal.ZERO) > 0) {
				Board oldBoard = repository.findByBoardNumber(board.getBoardNumber());
				oldBoard.setBoardTitle(board.getBoardTitle());
				oldBoard.setBoardContents(board.getBoardContents());
				
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
