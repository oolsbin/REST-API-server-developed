package com.example.demo.Jwt.test;

import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.msg.DefaultRes;
import com.example.demo.msg.ResponseMessage;
import com.example.demo.msg.StatusCode;

@Service
public class AuthService {
	
//	private final UserMapper userMapper;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	
	// 의존성 주입
    public AuthService(JwtService jwtService, PasswordEncoder passwordEncoder) {
//        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }
    
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        IdToken token = new IdToken(username, password);
//        return authenticationManager.authenticate(token);
//    }
    
    
    //로그인
    //DefaultRes는 클라이언트에게 응답을 보내기 위해 StatusCode, ResponseMessage, Data를 담은 클래스이다.
	public DefaultRes<TokenRes> signln(final JwtRequestDto dto) {
		final User user = userMapper.findById(dto.getId());
		
		// 회원 정보가 존재하지 않거나, 아이디가 틀렸음
        if (user == null) {
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
        }

        // 로그인 성공
        //PasswordEncoder는 비밀번호를 암호화하는 인터페이스이다.
        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            // 토큰 생성										//create메소드를 호출하여 토큰을 받아와 클라이언트에게 토큰을 반환한다.
            final TokenRes tokenDto = new TokenRes(jwtService
            		.create(user.getUsername());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }

        // 비밀번호가 틀렸을 때
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }
}