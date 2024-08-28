package com.wook.toy.services.answer;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wook.toy.domain.Answer;
import com.wook.toy.repository.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository repository;
	
	public Answer getAnswerInfo(Answer answer) {
		Answer answerInfo = repository.findByBoardNumberAndUseYn(answer.getBoardNumber(), answer.getUseYn());
		if(answerInfo != null && answerInfo.getBoardNumber() != BigDecimal.ZERO) {
			return answerInfo;
		} else {
			return null;
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal insertToUpdateAnswer(Answer answer) {
		try {
			Answer newAnswer = new Answer();
			
			if(answer != null && answer.getAnswerNumber() != null && answer.getAnswerNumber().compareTo(BigDecimal.ZERO) > 0) {
				Answer oldAnswer = repository.findByAnswerNumber(answer.getAnswerNumber());
				oldAnswer.setAnswerContent(answer.getAnswerContent());
				
				newAnswer = repository.save(oldAnswer);
			} else {
				newAnswer = repository.save(answer);
			}
			
			if(answer.getAnswerNumber().compareTo(BigDecimal.ZERO) > 0) {
				return newAnswer.getAnswerNumber();
			} else {
				return new BigDecimal(0);
			}
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			return new BigDecimal(0);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal deleteAnswer(BigDecimal answerNumber) {
		try {
			Answer answer = repository.findByAnswerNumber(answerNumber);
			answer.setUseYn("N");
			
			Answer newAnswer = repository.save(answer);
			if(newAnswer != null) {
				return newAnswer.getAnswerNumber();
			} else {
				return new BigDecimal(0);
			}
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			return new BigDecimal(0);
		}
		
	}
}
