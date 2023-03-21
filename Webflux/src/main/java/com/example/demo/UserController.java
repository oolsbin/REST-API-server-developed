package com.example.demo;


import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.exception.CustomException;
import com.example.demo.refresh.TokenVO;
import com.example.demo.token.JwtAccessService;
import com.example.demo.token.JwtRefreshService;
import com.example.demo.user.UserService;
import com.example.demo.user.UserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "로그인/회원가입")
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

	// post로 호출시 토큰발생
	@PostMapping("user/auth")
	@ApiOperation(value = "로그인", notes = "로그인 기능")
	// ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus,
	// HttpHeaders, HttpBody를 포함한다.
	public ResponseEntity<?> loginId(@RequestBody UserVO vo)
			throws Exception {

		UserVO user = new UserVO();

		user.setId(vo.getId());
		user.setPw(vo.getPw());

		if (userService.login(vo) == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		System.out.println(userService.login(vo).getPw());
		System.out.println(userService.login(vo).getId());
		if (!passwordEncoder.matches(vo.getPw(), userService.login(vo).getPw())) {
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
	@ExceptionHandler(CustomException.class)
	@PostMapping("user/register")
	@ApiOperation(value = "회원가입", notes = "회원가입 기능")
	public ResponseEntity<?> join(@RequestBody UserVO vo) throws Exception {
		
//		CustomException e = null;
//		CustomErrorCode errorCode = e.getErrorCode();
		
		//id 유효성 검사
		String id = userService.userId(vo); // 아이디 검색 결과
		if (!(id==null)) {//id가 있으면
			HttpStatus status = HttpStatus.UNAUTHORIZED;
	        String message = "이미 존재하는 아이디 입니다.";
			return new ResponseEntity<>(message, status);
		}
		
//		if (id.length() < 4 || id.length() > 12) {
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//			String message = "아이디를 4자리 초과 12자리 미만으로 작성해주세요.";
//			return new ResponseEntity<>(message, status);
//		}
//
//		String idRegex = "^[a-z0-9]+$";
//		Pattern pattern = Pattern.compile(idRegex);
//		Matcher matcher = pattern.matcher(id);
//		if (!matcher.matches()) {
//			// id가 영문 소문자, 숫자로만 구성되어 있지 않음
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//			String message = "id를 다시 입력해주세요.";
//			return new ResponseEntity<>(message, status);
//		}
//
//		if (vo.getPw().length() < 8 || vo.getPw().length() > 16) {
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//			String message = "pw를 8자리 이상 16자리 이하로 작성해주세요.";
//			return new ResponseEntity<>(message, status);
//		}
//
//		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!_])[A-Za-z\\d@$!%*?&]{8,16}$";
//		Pattern passwordpattern = Pattern.compile(passwordRegex);
//		Matcher passwordmatcher = passwordpattern.matcher(vo.getPw());
//		if (!passwordmatcher.matches()) {
//			// password가 영문 대소문자, 숫자, 특수문자로만 구성되어 있는지 확인
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//			String message = "pw를 다시 입력해주세요.";
//			return new ResponseEntity<>(message, status);
//		}
//
//		if (vo.getName().isEmpty()) {
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//			String message = "이름을 입력해주세요.";
//			return new ResponseEntity<>(message, status);
//		}
//
//		String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
//		Pattern emailpattern = Pattern.compile(emailRegex);
//		Matcher emailmatcher = emailpattern.matcher(vo.getPw());
//		if (!emailmatcher.matches()) {
//			// password가 영문 대소문자, 숫자, 특수문자로만 구성되어 있는지 확인
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//			String message = "이메일 형식에 맞게 다시 입력해주세요.";
//			return new ResponseEntity<>(message, status);
//		}


		userService.join(vo);
		StringBuffer msg = new StringBuffer();
		msg.append("회원가입을 환영합니다.^^");
		return ResponseEntity.ok().body(msg.toString());
	};
	

	//test
//	@GetMapping("/jwtTest")
//    public String jwtTest(@RequestHeader("User-Agent") String userAgent){
//        log.info("UserAgent = {}", userAgent);
//
//        return userAgent;
//    }
	
	@GetMapping("user/refresh")
	@ApiOperation(value = "로그인 인증", notes = "자동로그인이 가능하도록 사용자 token을 확인하는 기능")
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
	    	// accessToken 사용자정보 꺼내기 (id값)
			String[] splitToken = refreshToken.split("\\.");
			String payload = new String(Base64.getDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);
			JSONObject jsonObject = new JSONObject(payload);
			System.out.println(jsonObject);
			// user_id
			String id = jsonObject.getString("id");
			System.out.println(id);
			//////////////아이디 추출//////////////////////
	    	
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
				
				
				
//				String id_d = decodedJWT.getId();//id=null
			//refreshToken 저장
////////////////////////////////////////////////////////////////////////////////////////				
			String refreshId = userService.refreshToken_id_chk(id);
			if(refreshId!=null) {
	    			userService.refreshToken_delete(token_vo);
	    		}
			String re_refreshToken = refreshService.login(vo.getId(), vo.getPw());

			
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
