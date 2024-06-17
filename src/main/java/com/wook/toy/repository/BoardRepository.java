package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wook.toy.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, BigDecimal> {

	Page<Board> findByBoardSectionAndBoardTitleContaining(String boardSection, String searchKeyword, Pageable pageable);
	
	Board findByBoardNumber(BigDecimal boardNumber);
}
