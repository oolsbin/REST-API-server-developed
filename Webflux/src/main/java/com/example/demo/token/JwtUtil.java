package com.example.demo.token;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
	
	@Value("${JWT.ACCESS}")
	private String secreKey;

	@SuppressWarnings("deprecation")
	public static String createJwt(String id, String secretKey, Long expiredMs) {
//		Claims claims = Jwts.claims();//claims: 클라이언트에 대한 정보
//		claims.put("id", id);
		
		//token 생성
		return Jwts.builder()
				.setId(id)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact();		
	}
	
	 // String으로 된 코드를 복호화하여 signatur 검증처리
	  public Jws<Claims> getClaims(String refreshToken) {
	    try {
	      // 암호화 키로 복호화한다.
	      // 즉 암호화 키가 다르면 에러가 발생한다.
	      return Jwts.parser()
	                 .setSigningKey(secreKey)
	                 .parseClaimsJws(refreshToken);
	    } catch (RuntimeException e) {
	        if (e.getCause() instanceof SignatureException) {
	            return null;
	        }
	        throw e;
	    }
	  }
	  
	  // 토큰 검증 함수
	  public boolean validateToken(Jws<Claims> claims) {
	    // 토큰 만료 시간이 현재 시간을 지났는지 검증
	    return !claims.getBody()
	                  .getExpiration()
	                  .before(new Date());
	  }
	  
	  // 토큰을 통해 Payload의 ID를 취득
	  public String getKey(Jws<Claims> claims) {
	    // Id 취득
	    return claims.getBody()
	                 .getId();
	  }
	  
	  // 토큰을 통해 Payload의 데이터를 취득
//	  public Object getClaims(Jws<Claims> claims, String key) {
//	    // 데이터 취득
//	    return claims.getBody()
//	                 .get(key);
//	  }

}
