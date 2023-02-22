package com.example.demo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Jwt.security.JwtRequestDto;
import com.example.demo.Jwt.security.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
//	@RequestMapping(value="/login", method = RequestMethod.GET)
//	public void Login() {
//		System.out.println("/request /join : GET");
//	}
	
	//post로 호출시 토큰발생
	@PostMapping("/login")
	public ResponseEntity<String> Login
		(@RequestBody JwtRequestDto dto) {
		System.out.println(userService.login("id", "pw"));
		return ResponseEntity.ok().body(userService.login("", ""));	
	}

}
