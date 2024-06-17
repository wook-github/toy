package com.wook.toy.services.board;

import org.springframework.stereotype.Service;

import com.wook.toy.repository.BoardRepository;

@Service
public class BoardService {

	private final BoardRepository repository;
	
	public BoardService(BoardRepository repository) {
		this.repository = repository;
	}
}
