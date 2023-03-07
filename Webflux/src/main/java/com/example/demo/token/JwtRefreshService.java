package com.example.demo.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtRefreshService {
	@Value("${JWT.REFRESH}")
	private String secreKey;

	//token 기한 설정
	private Long expiredMs = 3000 * 70 * 60L;
	
	//login 정보 가져오기
	public String login(String id, String pw) {
		//인증과정 생략
		return JwtUtil.createJwt(id, secreKey, expiredMs);
	}
	
//	// 토큰 유효성 검증
//	public Boolean validationToken(String jwt){
//
//			if (jwt != null) {
//				
//					String userKey = this.get(jwt);
//					
//					String key = RedisClientTemplate.get(RedisSourceType.USER_KEY.getKey(userKey), redisTemplate);
//		
//					if (key.equals(jwt)) {
//						return true;
//					}			
//					return false;
//			}
//			return false;
//		}
//
//	// 토큰 만료 확인 
//	public Boolean getExpToken(String jwt) {
//			try {
//				Jws claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt);
//				
//				Date exp = claims.getBody().getExpiration();
//				
//				Date now = new Date();
//				
//				if (exp.after(now)) {
//					return true;		
//				}				
//				return false;
//			} catch (Exception e) {
//				return false;
////				UnauthorizedException("Illegal Token");
//			}
////			throw new NullArgumentException("Token is NULL");
//		}
}
