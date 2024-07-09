package com.wook.toy.services.member;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wook.toy.domain.Member;
import com.wook.toy.repository.MemberRepository;

import jakarta.annotation.Resource;

@Service
public class MemberService {

	@Resource(name="passwordEncoder")
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberRepository repository;
	
	public Member findByUser(HashMap<String, Object> param) {
		
		if(param.get("userId") != null && !"".equals(param.get("userId"))) {
			String userId = (String) param.get("userId");
			
			return repository.findByUserId(userId);
		} else {
			String userName = param.get("userName").toString();
			String userBirth = param.get("userBirth").toString().replaceAll("[-]", "");
			String userPhone = param.get("userPhone").toString().replaceAll("[-]", "");
			
			return repository.findByUserNameAndUserBirthAndUserPhone(userName, userBirth, userPhone);			
		}
	}
	
	public BigDecimal resetPw(HashMap<String, Object> param) {
		String userId = (String) param.get("userId");
		String userPassword = (String) param.get("userPassword");
		
		Member member = this.findByUser(param);
		
		member.setUserId(userId);
		member.setUserPassword(passwordEncoder.encode(userPassword));
		
		repository.save(member);
		
		return member.getUserNumber();
	}
	
	public Member checkPw(HashMap<String, Object> param) {
		Member member = this.findByUser(param);
		
		if(param.get("userPassword") != null && !"".equals(param.get("userPassword"))) {
			String inpUserPassword = (String) param.get("userPassword");
			if(passwordEncoder.matches(inpUserPassword, member.getUserPassword())) {
				return member;
			} else {
				return member.createMember();
			}
		} else {
			return member.createMember();
		}
		
	}
	
	public Boolean modifyUser(Member member) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		if(member != null && member.getUserId() != null && !"".equals(member.getUserId())) {
			param.put("userId", member.getUserId());
		}
		
		Member memberInfo = this.findByUser(param);
		
		if(member != null && member.getUserPassword() != null && !"".equals(member.getUserPassword())) {
			memberInfo.setUserPassword(passwordEncoder.encode(member.getUserPassword()));
		}
		
		if(member != null && member.getUserNickname() != null && !"".equals(member.getUserNickname())) {
			memberInfo.setUserNickname(member.getUserNickname());
		}
		
		memberInfo.setUserPhone(member.getUserPhone().replaceAll("[-]", ""));
		memberInfo.setUserBirth(member.getUserBirth().replaceAll("[-]", ""));
		
		member = repository.save(memberInfo);
		if(member != null && member.getUserNumber() != null && member.getUserNumber().compareTo(BigDecimal.ZERO) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean whdwlUser(HashMap<String, Object> param) {
		Member member = this.findByUser(param);
		
		member.setUseYn("N");
		
		member = repository.save(member);
		if(member.getUserNumber() != null && member.getUserNumber().compareTo(BigDecimal.ZERO) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Page<Member> getMemberList(HashMap<String, Object> param, Pageable pageable) {
		if(param.get("searchKeyword") != null && !"".equals(param.get("searchKeyword"))) {
			String searchKeyword = (String) param.get("searchKeyword");
			return repository.findByUserNameContaining(searchKeyword, pageable);
		} else {
			return repository.findAll(pageable);
		}
	}
	
}
