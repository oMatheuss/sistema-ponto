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

import com.sistemaponto.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@ComponentScan("com.sistemaponto")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationEntryPointImpl authenticationEntryPoint;
	
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
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers(HttpMethod.POST, "/login", "/cadastro").permitAll()
			.antMatchers(HttpMethod.GET, "/dados").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.PUT, "/dados/atualizar").hasAnyRole("ADMIN", "USER")
			.antMatchers(HttpMethod.GET, "/adm/**").hasRole("ADMIN")
			.anyRequest().authenticated().and()
			
			.formLogin()
			.usernameParameter("username").passwordParameter("password")
			.loginPage("/login").permitAll()
			.loginProcessingUrl("/login/process").permitAll()
			.defaultSuccessUrl("/").and()
			
			.rememberMe().alwaysRemember(true).tokenValiditySeconds(1800)
			.rememberMeCookieName("RememberMe").and()
			
			.logout().deleteCookies("RememberMe", "JSESSIONID")
			.logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
			
			.and()
			.exceptionHandling().accessDeniedPage("/accessdenied")
			.authenticationEntryPoint(authenticationEntryPoint).and()
			
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
			
			.csrf().disable();
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
}
