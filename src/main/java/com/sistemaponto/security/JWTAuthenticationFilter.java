package com.sistemaponto.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.sistemaponto.domain.Funcionario;
import com.sistemaponto.service.FuncionarioService;

public class JWTAuthenticationFilter extends GenericFilterBean {
	
	private FuncionarioService service;
	
	public JWTAuthenticationFilter(FuncionarioService service) {
		this.service = service;
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		String user = TokenAuthenticationService.getAuthenticatedUser(
				(HttpServletRequest) request);
		
		
		if (user != null) {
			TokenAuthenticationService.setNewAuthentication(
				(HttpServletRequest) request,
				(HttpServletResponse) response);
			
			Funcionario f = service.buscaPorUsername(user);
			
			 Authentication authentication = new UsernamePasswordAuthenticationToken(
					 user, null, f.toRole());

			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} else {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		
		filterChain.doFilter(request, response);
	}
}