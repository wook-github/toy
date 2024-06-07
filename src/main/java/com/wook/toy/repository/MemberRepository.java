package com.wook.toy.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wook.toy.domain.Member;

public interface MemberRepository extends JpaRepository<Member, BigDecimal> {

	Optional<Member> findByUserId(String userId);
	
	Member findByUserNameAndUserBirthAndUserPhone(String userName, String userBirth, String userPhone);
	
}
