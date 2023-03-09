package com.example.demo;


import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.refresh.TokenVO;
import com.example.demo.token.JwtAccessService;
import com.example.demo.token.JwtRefreshService;
import com.example.demo.user.UserService;
import com.example.demo.user.UserVO;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final JwtAccessService accessService;
	private final JwtRefreshService refreshService;

	private final UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@CrossOrigin(originPatterns = "http://172.30.1.40:3000")
	// post로 호출시 토큰발생
	@PostMapping("/login")
	// ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus,
	// HttpHeaders, HttpBody를 포함한다.
	public ResponseEntity<?> loginId(@RequestBody UserVO vo)
			throws Exception {

//		String path = "";

		UserVO user = new UserVO();

		user.setId(vo.getId());
		user.setPw(vo.getPw());

		//result왜 널값 들어오니.... ㅜㅜㅜ
//		UserVO result = userService.login(vo);
		
		if (userService.login(vo) == null) {
			StringBuffer msg = new StringBuffer();
			msg.append("로그인실패");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		if (!passwordEncoder.matches(vo.getPw(), userService.login(vo).getPw())) {
			StringBuffer msg = new StringBuffer();
			msg.append("패스워드 불일치");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);//400
		}
		
		HashMap<String, String> map = new HashMap<String, String>() {{
				put("access", accessService.login(vo.getId(), vo.getPw()));
				put("refresh", refreshService.login(vo.getId(), vo.getPw()));
			}};
			
		//refreshToken 저장
		String refreshToken = refreshService.login(vo.getId(), vo.getPw());
		String id = vo.getId();
		
		TokenVO token_vo = new TokenVO();
		Date today = new Date();
		
		token_vo.setId(id);
		token_vo.setRefreshToken(refreshToken);
		token_vo.setCreateDate(today);
		token_vo.setUpdateDate(today);
		userService.refreshToken(token_vo);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(map);//201
	}
	

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserVO vo) throws Exception {

		if (userService.userId(vo) == 1) {
			
			StringBuffer msg = new StringBuffer();
			msg.append("가입에 실패했습니다 ㅜ");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		userService.join(vo);
		StringBuffer msg = new StringBuffer();
		msg.append("회원가입을 환영합니다.^^");
		return ResponseEntity.ok().body(msg.toString());
	};
	

	//test
	@GetMapping("/jwtTest")
    public String jwtTest(@RequestHeader("User-Agent") String userAgent){
        log.info("UserAgent = {}", userAgent);

        return userAgent;
    }
	
	@GetMapping("/refresh")
	public ResponseEntity<String> getUserFromToken(@RequestHeader HttpHeaders headers) throws Exception {
		//1)client에서 refreshToken을 header에 담아서 보내면 그 token을 꺼내서 사용한다
	    String authToken = headers.getFirst("Authorization");
	    if(authToken != null & authToken.startsWith("Bearer ")) {
	    	String refreshToken = null;
	    	refreshToken = authToken.substring(7);
	    	System.out.println(refreshToken);
	    	//2)client에서 받은 token을 받아서 token_vo에 담는다
	    	TokenVO token_vo = new TokenVO();
	    	token_vo.setRefreshToken(refreshToken);
	    	//3)token이 db에 있다면 유효성 검사를 실시한다
	    	if(userService.refreshToken_chk(token_vo)!=null) {
	    		
	    		//유효성 검사
	    		DecodedJWT decodedJWT = JWT.decode(refreshToken);
	    		Date date = new Date();
	    		Date yom = decodedJWT.getExpiresAt();
	    		//4-1)만약에 받은 token의 유효기간이 오늘까지이면 "기간이 만료되었습니다"를 return한다
	    		if(decodedJWT.getExpiresAt().before(date)) {
	    			
	    			
	    			return new ResponseEntity<>("기간이 만료되었습니다", HttpStatus.OK);
	    		}
	    		
//	    		userService.refreshToken_delete(token_vo);

	    		//4-2)token의 유효기간이 남아 있으면 refreshToken과 accessToken을 재발급한다
	    		UserVO vo = new UserVO();
	    		
	    		//이미 있던걸 지우고 새로 만들어서 저장 (갱신)
	    		HashMap<String, String> map = new HashMap<String, String>() {{
					put("access", accessService.login(vo.getId(), vo.getPw()));
					put("refresh", refreshService.login(vo.getId(), vo.getPw()));
				}};
				
				
				
				
			//refreshToken 저장
			String re_refreshToken = refreshService.login(vo.getId(), vo.getPw());
			String id = vo.getId();
			
			TokenVO re_token_vo = new TokenVO();
			Date today = new Date();
			
			token_vo.setId(id);
			token_vo.setRefreshToken(re_refreshToken);
			token_vo.setCreateDate(today);
			token_vo.setUpdateDate(today);
			userService.refreshToken(token_vo);
			
			return ResponseEntity.ok().body(map+"");
	    		
//	    		return new ResponseEntity<>("refreshToken 요청을 받았습니다 = " + refreshToken + "\nBearer 토큰이 유효합니다.", HttpStatus.OK);
	    	}else {	    		
	    		//로그인 하라고 틩겨서
	    		StringBuffer msg = new StringBuffer();
				msg.append("Bearer 토큰이 필요합니다.");
				return ResponseEntity.ok().body(msg.toString());
	    	}

//	    	return new ResponseEntity<>("refreshToken 요청을 받았습니다 = " + refreshToken + "\nBearer 토큰이 유효합니다.", HttpStatus.OK);
//	    }else {
//	    	return new ResponseEntity<>("Bearer 토큰이 필요합니다.", HttpStatus.UNAUTHORIZED);
	    			
	    }
		return null;
	}

}
