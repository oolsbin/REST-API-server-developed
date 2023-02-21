//package com.example.demo;
//
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.msg.DefaultRes;
//import com.example.demo.msg.LoginReq;
//import com.example.demo.msg.ResponseMessage;
//import com.example.demo.msg.StatusCode;
//import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

//import com.example.demo.Jwt.Dto.JwtRequestDto;
//import com.example.demo.Jwt.Dto.MemberSignupRequestDto;
//import com.example.demo.Jwt.User.UserService;


//@RestController
//@RequestMapping("/users")
//public class AuthController {
//	
//	private UserService userservice;

	//로그인: /auth/login
	//http post body -> data를 집어넣어서 받겠다.
//    @PostMapping("login")
//    public ResponseEntity login(@RequestBody LoginReq loginReq) {
//        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginReq),HttpStatus.OK);
//    }
//
////    회원가입: /auth/signup
//    @PostMapping(value = "signup", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//    public String signup(@RequestBody MemberSignupRequestDto request) {
//        return "signup";
//    }
//}
