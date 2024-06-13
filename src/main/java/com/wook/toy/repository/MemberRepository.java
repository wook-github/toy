package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wook.toy.domain.Member;

public interface MemberRepository extends JpaRepository<Member, BigDecimal> {

	Member findByUserId(String userId);
	
	Member findByUserNameAndUserBirthAndUserPhone(String userName, String userBirth, String userPhone);
	
}
