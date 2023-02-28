//package com.example.demo;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.Jwt.test.AuthService;
//import com.example.demo.Jwt.test.JwtRequestDto;
//import com.example.demo.msg.DefaultRes;
//import com.example.demo.msg.ResponseMessage;
//import com.example.demo.msg.StatusCode;
//
//import lombok.extern.slf4j.Slf4j;
//
//////import com.example.demo.msg.DefaultRes;
//////import com.example.demo.msg.LoginReq;
//////import com.example.demo.msg.ResponseMessage;
//////import com.example.demo.msg.StatusCode;
//////import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
////
//////import com.example.demo.Jwt.Dto.JwtRequestDto;
//////import com.example.demo.Jwt.Dto.MemberSignupRequestDto;
//////import com.example.demo.Jwt.User.UserService;
//
//@Slf4j
//@RestController
//public class LoginController {
//	
//	//실패시 사용
//	private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
//	
//	private final AuthService authService;
//	
//	//생성자 의존성 주입
//	public LoginController(AuthService authService) {
//		this.authService = authService;
//	}
//
//	//로그인
//	//http post body -> data를 집어넣어서 받겠다.
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody JwtRequestDto dto) {
//    	 try {
//             return new ResponseEntity(authService.signln(dto), HttpStatus.OK);
//         } catch (Exception e) {
//             log.error(e.getMessage());
//             return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//    }
//
////    회원가입: /auth/signup
////    @PostMapping(value = "signup", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
////    public String signup(@RequestBody MemberSignupRequestDto request) {
////        return "signup";
////    }
//}
