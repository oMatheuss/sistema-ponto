package com.sistemaponto.security.model;

import java.util.*;
 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

 
public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = -3397208813826094124L;
	
	private User user;
     
    public MyUserDetails(User user) {
        this.user = user;
    }
    
    public MyUserDetails(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        this.user = new User(principal, credentials, authorities);
    }
 
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }
 
    @Override
    public String getPassword() {
        return user.getPassword();
    }
 
    @Override
    public String getUsername() {
        return user.getUsername();
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
        return user.isEnabled();
    }
 
}