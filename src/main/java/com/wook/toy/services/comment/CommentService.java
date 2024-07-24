package com.wook.toy.services.comment;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wook.toy.domain.Comment;
import com.wook.toy.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository repository;
	
	public Page<Comment> getCommentList(HashMap<String, Object> param, Pageable pageable) {
		if(param != null && (param.get("boardNumber") != null && !"".equals(param.get("boardNumber")))
				&& (param.get("useYn") != null && !"".equals(param.get("useYn")))) {
			
			BigDecimal boardNumber = new BigDecimal((String) param.get("boardNumber"));
			String useYn = (String) param.get("useYn");
			return repository.findByBoardNumberAndUseYn(boardNumber, useYn, pageable);
		} else {
			System.out.println("검색 조회 실패");
			return Page.empty();
		}
		
	}
	
	public Comment getCommentInfo(BigDecimal commentNumber) {
		Comment commentInfo = repository.findByCommentNumber(commentNumber);
		if(commentInfo != null && commentInfo.getCommentNumber() != BigDecimal.ZERO) {
			return commentInfo;
		} else {
			return new Comment();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal insertToUpdateComment(Comment comment) {
		try {
			Comment newComment = new Comment();
			
			if(comment != null && comment.getCommentNumber() != null && comment.getCommentNumber().compareTo(BigDecimal.ZERO) > 0) {
				Comment oldComment = repository.findByCommentNumber(comment.getCommentNumber());
				oldComment.setCommentContent(comment.getCommentContent());
				
				newComment = repository.save(oldComment);
			} else {
				newComment = repository.save(comment);
			}
			
			if(newComment.getCommentNumber().compareTo(BigDecimal.ZERO) > 0) {
				return newComment.getCommentNumber();
			} else {
				return new BigDecimal(0);
			}
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			return new BigDecimal(0);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public BigDecimal deleteComment(BigDecimal commentNumber) {
		try {
			Comment comment = repository.findByCommentNumber(commentNumber);
			comment.setUseYn("N");
			
			Comment newComment = repository.save(comment);
			if(newComment != null) {
				return newComment.getCommentNumber();
			} else {
				return new BigDecimal(0);
			}
		} catch(DataAccessException e) {
			System.out.println(e.getMessage());
			return new BigDecimal(0);
		}
		
	}
}
