package com.example.demo.Jwt.test;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import lombok.extern.slf4j.Slf4j;

//먼저 Service 계층에 JWT 토큰을 만들어주는 클래스
@Slf4j
@Service
public class JwtService {

	//application.properites
	@Value("${JWT.ISSUER}")
	private String ISSUER;

	@Value("${JWT.SECRET}")
	private String SECRET;

	/**
	 * 토큰 생성
	 *
	 * @param userIdx 토큰에 담길 로그인한 사용자의 회원 고유 IDX
	 * @return 토큰
	 * @throws UnsupportedEncodingException
	 * @throws IllegalArgumentException
	 */
	
	// 플래그 true false boolean
	public String create(final int userIdx) throws IllegalArgumentException, UnsupportedEncodingException {
		
		try {
			JWTCreator.Builder b = JWT.create();
			// 토큰 발급자
			b.withIssuer(ISSUER);
			// 토큰 payload 작성, key - value 형식, 객체도 가능
			b.withClaim("userIdx", userIdx);
			// 토큰 만료날짜 지정
			b.withExpiresAt(expiresAt());
			return b.sign(Algorithm.HMAC256(SECRET));
		} catch (JWTCreationException jwtCreationException) {
			log.info(jwtCreationException.getLocalizedMessage());
		}
		return null;
	}

	private Date expiresAt() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());
		// 한달 24 * 31
		cal.add(Calendar.HOUR, 744);
		return (Date) cal.getTime();
	}
	
	
	/**
     * 토큰 해독
     *
     * @param token 토큰
     * @return 로그인한 사용자의 회원 고유 IDX
     */

//    public Token decode(final String token) {
//        try {
//            // 토큰 해독 객체 생성
//            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
//            // 토큰 검증
//            DecodedJWT decodedJWT = jwtVerifier.verify(token);
//            // 토큰 payload 반환, 정상적인 토큰이라면 토큰 사용자 고유 ID, 아니라면 -1
//            return new Token(decodedJWT.getClaim("userIdx").asLong().intValue());
//        } catch (JWTVerificationException jve) {
//            log.error(jve.getMessage());
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return new TOKEN();
//    }

}