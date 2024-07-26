package com.wook.toy.repository;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wook.toy.domain.Member;

public interface MemberRepository extends JpaRepository<Member, BigDecimal> {

	Page<Member> findByUserNameContaining(String userName, Pageable pageable);
	
	Member findByUserId(String userId);
	
	Member findByUserIdAndUserPassword(String userId, String userPassword);
	
	Member findByUserPhoneAndUseYn(String userPhone, String useYn);
	
	Member findByUserNameAndUserBirthAndUserPhone(String userName, String userBirth, String userPhone);
	
}
