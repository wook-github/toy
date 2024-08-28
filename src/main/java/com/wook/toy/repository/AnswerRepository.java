package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wook.toy.domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, BigDecimal> {

	Answer findByBoardNumber(BigDecimal boardNumber);
	
	Answer findByBoardNumberAndUseYn(BigDecimal boardNumber, String useYn);
	
	Answer findByAnswerNumber(BigDecimal answerNumber);

}
