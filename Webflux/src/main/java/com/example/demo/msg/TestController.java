package com.example.demo.msg;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class TestController {
//	
//	private UserService userservice;

	//로그인: /auth/login
	//http post body -> data를 집어넣어서 받겠다.
    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginReq loginReq) {
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginReq),HttpStatus.OK);
    }
}