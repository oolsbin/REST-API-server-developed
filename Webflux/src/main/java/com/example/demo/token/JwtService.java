//package com.example.demo.token;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JwtService {
//	
//	@Value("${JWT.SECRET}")
//	private String secreKey;
//
//	//token 기한 설정
//	private Long expiredMs = 1000 * 60 * 60L;
//	
//	//login 정보 가져오기
//	public String login(String id, String password) {
//		//인증과정 생략
//		return JwtUtil.createJwt(id, secreKey, expiredMs);
//	}
//
//}
