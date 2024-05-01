package com.wook.toy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wook.toy.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUserId(String userId);
}
