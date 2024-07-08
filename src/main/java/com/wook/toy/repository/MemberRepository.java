package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wook.toy.domain.Member;

public interface MemberRepository extends JpaRepository<Member, BigDecimal> {

	Member findByUserId(String userId);
	
	Member findByUserIdAndUserPassword(String userId, String userPassword);
	
	Member findByUserNameAndUserBirthAndUserPhone(String userName, String userBirth, String userPhone);
	
}
