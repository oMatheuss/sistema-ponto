package com.sistemaponto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sistemaponto.security.service.UserDetailsServiceImpl;
import com.sistemaponto.service.FuncionarioService;

@Configuration
@EnableWebSecurity
@ComponentScan("com.sistemaponto")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
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
			.antMatchers("/login/process", "/cadastrar").permitAll()
			.antMatchers(HttpMethod.GET, "/dados").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.PUT, "/dados/atualizar").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.GET, "/adm/**").hasRole("ADMIN")
			.anyRequest().authenticated()
			
			.and()
			.exceptionHandling().accessDeniedPage("/accessdenied")
			.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
			
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			
			.formLogin().disable()
			.httpBasic().disable()
			
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
