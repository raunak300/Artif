package com.rbm.artif.security;

import com.rbm.artif.Aspect.ServiceExceptionHandler;
import com.rbm.artif.entity.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {
	private final Users user;

	public CustomUserDetails(Users user) {
		this.user = user;
	}

	private static final Logger logger = LogManager.getLogger(CustomUserDetails.class);


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		System.out.println("USER ROLE: " + user.getUserPremium());
		logger.atInfo().log("user role:"+user.getUserPremium());
		return List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserPremium().name()));
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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

	public Users getUser() {
		return user;
	}
}
