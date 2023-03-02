package com.example.demo;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.token.JwtRequestDto;
import com.example.demo.user.UserService;
import com.example.demo.user.UserVO;
import com.example.demo.token.JwtAccessService;
import com.example.demo.token.JwtRefreshService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final JwtAccessService accessService;
	private final JwtRefreshService refreshService;
	
	private final UserService userService;
	
//	@RequestMapping(value="/login", method = RequestMethod.GET)
//	public void Login() {
//		System.out.println("/request /join : GET");
//	}
	
	//post로 호출시 토큰발생
	@PostMapping("/login_token")
	//ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함한다. 
	public ResponseEntity<HashMap<String, String>> login
		(@RequestBody  JwtRequestDto vo)throws Exception {

		HashMap<String, String> map = new HashMap<String, String>(){{
			put("access", accessService.login(vo.getId(), vo.getPw()));
			put("refresh", refreshService.login(vo.getId(), vo.getPw()));
		}};
		return ResponseEntity.ok().body(map);	
	}
	
	
	//회원가입
	@ResponseBody
	@RequestMapping("/join")
	public ResponseEntity<?> join(@RequestBody UserVO vo) throws Exception {
		
		StringBuffer msg = new StringBuffer("<script>");
		if (userService.join(vo) == 1) {
			msg.append("alert('회원가입을 환영합니다.^^'); location='").append("'");
		} else {
			msg.append("alert('가입에 실패했습니다ㅠㅠ'); history.go(-1);");
		}
		msg.append("</script>");

		return ResponseEntity.ok().body(msg.toString());
	};
	

}
