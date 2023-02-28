package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.token.JwtRequestDto;
import com.example.demo.token.JwtService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final JwtService userService;
	
//	@RequestMapping(value="/login", method = RequestMethod.GET)
//	public void Login() {
//		System.out.println("/request /join : GET");
//	}
	
	//post로 호출시 토큰발생
	@PostMapping("/login_token")
	//ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함한다. 
	public ResponseEntity<String> Login
		(@RequestBody JwtRequestDto vo)throws Exception {
		
		System.out.println(userService.login("id", "pw"));
		System.out.println(vo.getId());
		System.out.println(vo.getPw());
		return ResponseEntity.ok().body(userService.login("", ""));	
	}

}
