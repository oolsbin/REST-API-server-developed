//package com.example.demo.Jwt.security;
//
//import javax.naming.AuthenticationException;
//
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.mariadb.jdbc.internal.protocol.authentication.AuthenticationProviderHolder.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Component
////인증관리자(AuthenticationManager)는 적절한 Provider(AuthenticationProvider)에게 인증처리를 위임
//public class CustomAuthProvider implements AuthenticationProvider{
//	
//	private final UserDetailsService userDetailsService;
//	private final PasswordEncoder passwordEncoder;
//	
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		
//		//???:loadUserByUsername(username)메서드를 호출해서 유저객체를 요청한다
//		
//		String username = authentication.getName();
//		String password = (String) authentication.getCredetials();
//		
//		                        //UserDetailsService 인터페이스에게 loadUserByUsername(username) 요청
//		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//		
//		//???:
//		//1)Repository에 findById() 메서드로 유저 객체 조회를 한다. 
//		//2)만약, 해당 유저객체가 존재하지 않으면 UsernameNotFoundException이 발생하고 UsernamePasswordAuthenticationFIlter에서 예외를 처리한다.
//		//→ FailHandler()에서 후속처리
//		//3)존재한다면 UserDetails 타입으로 반환된다.(Member 객체도 UserDetails 객체로 변환되어 반환)
//		
//		//Password검사
//		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
//			throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
//		}
//		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	}
//	
////	@Override
////	public boolean supports(Class<?> authentication) {
////        return true;
////    }
//}
