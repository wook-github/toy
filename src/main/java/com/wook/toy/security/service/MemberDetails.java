package com.wook.toy.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.wook.toy.domain.Member;

public class MemberDetails implements UserDetails {
	
	private static final long serialVersionUID = 5137215329200165790L;
	
	private final Member member;
	
	public MemberDetails(Member member) {
		this.member = member;
	}
	
	public Member getMember() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(member.getUserRole()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return member.getUserPassword();
	}

	@Override
	public String getUsername() {
		return member.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
