//package com.example.demo.Jwt.dto;
//
//import java.util.Date;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//public class JwtProvider {
//	
//public Token createAccessToken(String id, java.util.List<String> roles) {
//		
//		Claims claims = Jwts.claims().setSubject(id);//JWT payload에 저장되는 정보
//		claims.put("roles", roles);
//		Date now = new Date();
//		
//		String accessToken = Jwts.builder()
//				.setClaims(claims)
//				.setIssuedAt(now)
//				.setExpiration(new Date(now.getTime() + accessTokenValidTime))
//				.signWith(SignatureAlgorithm.HS256, accessSecretKey)
//				.compact();
//		
//		//Refresh Token
//        String refreshToken =  Jwts.builder()
//                .setClaims(claims) // 정보 저장
//                .setIssuedAt(now) // 토큰 발행 시간 정보
//                .setExpiration(new Date(now.getTime() + refreshTokenValidTime)) // set Expire Time
//                .signWith(SignatureAlgorithm.HS256, refreshSecretKey)  // 사용할 암호화 알고리즘과
//                // signature 에 들어갈 secret값 세팅
//                .compact();
//
//        return Token.builder().accessToken(accessToken).refreshToken(refreshToken).key(id).build();
//
//
//	}
//
//}
