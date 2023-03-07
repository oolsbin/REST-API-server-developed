//package com.example.demo.newjwt;
//
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertThrows;
//
//import java.security.InvalidParameterException;
//
//import io.jsonwebtoken.Claims;
//
//public class TestJwtSample {
//  // secret key가 짧으면 에러가 난다
//  private final String secretKey = "secretKey-test-authorization-jwt-manage-token";
//
//
//  void successTest() {
//    // given
//    JwtManager jwtManager = new JwtManager(secretKey, 1l, 60l);
//
//    // when
//    String username = "testuser-1";
//    String accessToken = jwtManager.generateAccessToken(username);
//
//    Claims claims = jwtManager.validTokenAndReturnBody(accessToken);
//    System.out.println("claims = " + claims);
//    String findUsername = claims.get("username", String.class);
//
//    // then
//    assertThat(username).isEqualTo(jwtManager.getName(accessToken));
//    assertThat(username).isEqualTo(findUsername);
//  }
//
//
//
//  void expireTokenTest() throws InterruptedException {
//    // given
//    JwtManager jwtManager = new JwtManager(secretKey, 1l, 60l);
//    String username = "testuser-1";
//    String accessToken = jwtManager.generateAccessToken(username);
//
//    // 2초 딜레이
//    Thread.sleep(2000l);
//
//    // when
//    InvalidParameterException ex = assertThrows(
//        InvalidParameterException.class
//        , () -> jwtManager.validTokenAndReturnBody(accessToken));
//
//    // then
//    assertThat(ex.getMessage()).isEqualTo("유효하지 않은 토큰입니다");
//  }
//}