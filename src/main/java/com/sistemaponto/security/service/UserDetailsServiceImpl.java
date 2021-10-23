package com.sistemaponto.security.service;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistemaponto.domain.Funcionario;
import com.sistemaponto.security.MyUserDetails;
import com.sistemaponto.service.FuncionarioService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private FuncionarioService funcServ;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Funcionario user;
		try {
			user = funcServ.buscaPorUsername(username);
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Usuario não encontrado!");
		}
		
		return new MyUserDetails(user.getUsername(),
				user.getSenha(),
				user.toRole());
	}
}
