//package com.example.demo.Jwt.security;
//
//import javax.naming.AuthenticationException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import lombok.extern.slf4j.Slf4j;
//
////Request Login
////클라이언트 측에서 Request Parameter로 username/password를 포함해 로그인 요청시
////구현한 UsernamePasswordAuthenticationFilter가 동작하여 AuthenticationToken을 생성한 후
////AuthenticationManager에게 인증 요청
//
//@Slf4j
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//	private final AuthenticationManager authenticationManager;
//	
//	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//	}
//	
////  Authentication(id + password)인증객체 생성
////	@Override
//	public org.springframework.security.core.Authentication attempAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//		String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
//        //AuthenticationManager에게 인증객체를 넘기며 인증처리를 위임함
//        return authenticationManager.authenticate(token);
//	}
//}
