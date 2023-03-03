package com.example.demo;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.token.JwtRequestDto;
import com.example.demo.token.JwtTokenDto;
import com.example.demo.user.UserService;
import com.example.demo.user.UserVO;
import com.example.demo.token.JwtAccessService;
import com.example.demo.token.JwtRefreshService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final JwtAccessService accessService;
	private final JwtRefreshService refreshService;

	private final UserService userService;

//	@RequestMapping(value="/login", method = RequestMethod.GET)
//	public void Login() {
//		System.out.println("/request /join : GET");
//	}

	// post로 호출시 토큰발생
	@PostMapping("/login_token")
	// ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus,
	// HttpHeaders, HttpBody를 포함한다.
	public ResponseEntity<?> login(@RequestBody UserVO vo) throws Exception {

		if (userService.login(vo) == null) {
			StringBuffer msg = new StringBuffer();
			msg.append("로그인실패");
			return ResponseEntity.ok().body(msg.toString());

		} else if(userService.login(vo)!=null){
			HashMap<String, String> map = new HashMap<String, String>() {{
					put("access", accessService.login(vo.getId(), vo.getPw()));
					put("refresh", refreshService.login(vo.getId(), vo.getPw()));
				}};
			return ResponseEntity.ok().body(map);
		}
		return ResponseEntity.ok().body(vo);
	}

	
	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserVO vo) throws Exception {

		StringBuffer msg = new StringBuffer();
		if (userService.join(vo) == 1) {
			// vo에 refresh token만들어서 저장하면 안되나..
//			vo.setRefreshToken(accessService.login(vo.getId(), vo.getPw()));
			msg.append("회원가입을 환영합니다.^^");
		} else {
			msg.append("가입에 실패했습니다ㅠㅠ");
		}
		return ResponseEntity.ok().body(msg.toString());
	};

}
