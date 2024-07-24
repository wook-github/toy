package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wook.toy.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, BigDecimal> {

	Page<Comment> findByBoardNumberAndUseYn(BigDecimal boardNumber, String useYn, Pageable pageable);
	
	Comment findByCommentNumber(BigDecimal commentNumber);
}
