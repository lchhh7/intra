package com.jinjin.jintranet.security.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jinjin.jintranet.model.Member;

public class PrincipalDetail implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	public BCryptPasswordEncoder encoder;
	
	private Member member;
	
	public PrincipalDetail(Member member) {
		this.member = member;
	}
	
	public Member getMember() {
		return member;
	}
	
	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getMemberId();
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
		if(member.getEdf() != null) {
			return false;
		}
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{ return "ROLE_"+member.getRole();});
		
		return collectors;
	}
}
