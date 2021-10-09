package com.sistemaponto.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sistemaponto.security.service.UserDetailsServiceImpl;
import com.sistemaponto.service.FuncionarioService;

@Configuration
@EnableWebSecurity
@ComponentScan("com.sistemaponto")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
     
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
    
    @Autowired
	private FuncionarioService service;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().cors().and()
			.authorizeRequests()
			.antMatchers("/login/**", "/cadastrar", "/sub/**").permitAll()
			.antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.GET, "/adm/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			
			.and()
			.exceptionHandling().accessDeniedPage("/error").and()
			
			.formLogin().disable()
			.httpBasic().disable()
			.sessionManagement().sessionFixation().newSession().and()
			
			// filtra requisições de login
			.addFilterBefore(new JWTSigninFilter("/login/process", authenticationManager()),
	                UsernamePasswordAuthenticationFilter.class)
			
			// filtra outras requisições para verificar a presença do JWT no header
			.addFilterBefore(new JWTAuthenticationFilter(service),
	                UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		// cria uma conta default
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password(new BCryptPasswordEncoder().encode("admin"))
			.roles("ADMIN");
	}
}
