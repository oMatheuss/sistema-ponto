package com.sistemaponto.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenAuthenticationService {
	
	// EXPIRATION_TIME = 30 minutos
	static final long EXPIRATION_TIME = 1_800_000;
	static final String SECRET = "AaBbCc";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	static void addAuthentication(HttpServletResponse response, String username) {
		String JWT = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}
	
	static String getAuthenticatedUser(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			String user;
			try {
				// faz parse do token
				user = Jwts.parser()
						.setSigningKey(SECRET)
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody()
						.getSubject();
			} catch (MalformedJwtException | 
					UnsupportedJwtException | 
					SignatureException | 
					ExpiredJwtException | 
					IllegalArgumentException e) {
				return null;
			}
			
			if (user != null) {
				return user;
			}
		}
		return null;
	}
	
	static void setNewAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String token = request.getHeader(HEADER_STRING);
		Claims jwts = Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
				.getBody();
		
		long exp = jwts.getExpiration().getTime();
		long now = System.currentTimeMillis();
		
		if (exp-now < 300_000) {
			addAuthentication(response, jwts.getSubject());
		} else {
			response.setHeader(HEADER_STRING, token);
		}
	}
}