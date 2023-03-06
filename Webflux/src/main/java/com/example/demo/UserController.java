package com.example.demo;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.refresh.TokenVO;
import com.example.demo.token.JwtAccessService;
import com.example.demo.token.JwtRefreshService;
import com.example.demo.user.UserService;
import com.example.demo.user.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {


	private final JwtAccessService accessService;
	private final JwtRefreshService refreshService;

	private final UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
			return ResponseEntity.ok().body(msg.toString());
		}
		
		if (!passwordEncoder.matches(vo.getPw(), userService.login(vo).getPw())) {
			StringBuffer msg = new StringBuffer();
			msg.append("패스워드 불일치");
			return ResponseEntity.ok().body(msg.toString());
		}
		
		HashMap<String, String> map = new HashMap<String, String>() {{
				put("access", accessService.login(vo.getId(), vo.getPw()));
				put("refresh", refreshService.login(vo.getId(), vo.getPw()));
			}};
			
		//refreshToken 저장
//		String refreshToken = refreshService.login(vo.getId(), vo.getPw());
//		TokenVO result = userService.refreshToken(null);
		
		return ResponseEntity.ok().body(map);
	}
	
	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserVO vo) throws Exception {

		if (userService.userId(vo) == 1) {
			
			StringBuffer msg = new StringBuffer();
			msg.append("가입에 실패했습니다 ㅜ");
			return ResponseEntity.ok().body(msg.toString());
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
	
	@GetMapping("/example")
	public ResponseEntity<String> getUserFromToken(@RequestHeader HttpHeaders headers) {
	    String authToken = headers.getFirst("Authorization");
	    if(authToken != null & authToken.startsWith("Bearer ")) {
	    	String token = null;
	    	token = authToken.substring(7);
	    	return new ResponseEntity<>("Bearer 토큰이 유효합니다.", HttpStatus.OK);
	    }else {
	    	return new ResponseEntity<>("Bearer 토큰이 필요합니다.", HttpStatus.UNAUTHORIZED);
	    			
	    }
	}

}
