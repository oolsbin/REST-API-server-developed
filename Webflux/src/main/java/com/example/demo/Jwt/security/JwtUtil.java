package com.example.demo.Jwt.security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	public static String createJwt(String id, String secreKey, Long expiredMs) {
		Claims claims = Jwts.claims();
		claims.put("id", id);
		
		//token 생성
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(SignatureAlgorithm.HS256, secreKey)
				.compact();
				
	}

}
