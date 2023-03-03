package com.example.demo;

import java.util.HashMap;

import javax.validation.constraints.Null;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	

	@GetMapping("/login")
	public String Login() {
		System.out.println("/request /join : GET");
		return "login";
	}
	
//		if (userService.login(vo) == null) {
//			StringBuffer msg = new StringBuffer();
//			msg.append("로그인실패");
//			return ResponseEntity.ok().body(msg.toString());
//
//		} else if(userService.login(vo)!=null){
//			return ResponseEntity.ok().body(map);
//		}	
	
	// post로 호출시 토큰발생
	@PostMapping("/login_token")
	// ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus,
	// HttpHeaders, HttpBody를 포함한다.
	public ResponseEntity<?> loginId(@RequestBody UserVO vo) throws Exception {

        String path = "";

        UserVO user = new UserVO();

        user.setId(vo.getId());
        user.setPw(vo.getPw());

        int result = userService.login(vo);

        if(result == 1) {
        	HashMap<String, String> map = new HashMap<String, String>() {{
					put("access", accessService.login(vo.getId(), vo.getPw()));
					put("refresh", refreshService.login(vo.getId(), vo.getPw()));
				}};

		return ResponseEntity.ok().body(map);
        } else {
			StringBuffer msg = new StringBuffer();
			msg.append("로그인실패");
			return ResponseEntity.ok().body(msg.toString());
        }	
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
