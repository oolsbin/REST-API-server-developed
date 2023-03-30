package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.example.demo.service.JwtAccessService;
import com.example.demo.service.JwtRefreshService;
import com.example.demo.service.UserService;
import com.example.demo.token.JwtUtil;
import com.example.demo.vo.TokenVO;
import com.example.demo.vo.UserInfoVO;
import com.example.demo.vo.UserVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "로그인/회원가입")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private final JwtAccessService accessService;
	private final JwtRefreshService refreshService;

	private final UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	// post로 호출시 토큰발생
	@PostMapping(value = "user/auth", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "로그인", notes = "로그인 기능")
	// ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus,
	// HttpHeaders, HttpBody를 포함한다.
	public ResponseEntity<?> loginId(@RequestBody UserVO vo) throws Exception {

		UserVO user = new UserVO();

		user.setId(vo.getId());
		user.setPw(vo.getPw());

		if (userService.login(vo) == null) {
			
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			Map<String, Object> map = new HashMap<>();
			map.put("status", HttpStatus.UNAUTHORIZED);
			map.put("msg", "아이디가 존재하지 않습니다.");
			log.info("================================= register response:\n" + gson.toJson(map));
			return new ResponseEntity<>(map, status);
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);// 400
		}
//		System.out.println(userService.login(vo).getPw());
//		System.out.println(userService.login(vo).getId());
		if (!passwordEncoder.matches(vo.getPw(), userService.login(vo).getPw())) {
			HttpStatus status = HttpStatus.UNAUTHORIZED;
			Map<String, Object> map = new HashMap<>();
			map.put("status", HttpStatus.UNAUTHORIZED);
			map.put("msg", "패스워드가 일치하지 않습니다.");
			log.info("================================= register response:\n" + gson.toJson(map));
			return new ResponseEntity<>(map, status);
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);// 400
		}

		HashMap<String, String> map = new HashMap<String, String>() {
			{
				put("access", accessService.login(vo.getId(), vo.getPw()));
				put("refresh", refreshService.login(vo.getId(), vo.getPw()));
				
			}
		};
		
		Map<String, Object> response = new HashMap<>();
		response.put("token", map);
		response.put("status", HttpStatus.OK);
		response.put("msg", "로그인 되었습니다.");

		// refreshToken 저장
		String refreshToken = refreshService.login(vo.getId(), vo.getPw());
		String id = vo.getId();

		TokenVO token_vo = new TokenVO();
		Date today = new Date();

		token_vo.setId(id);
		token_vo.setRefreshToken(refreshToken);
		token_vo.setCreateDate(today);
		token_vo.setUpdateDate(today);
		String uuid = RandomStringUtils.random(36, true, true);
		token_vo.setRefreshTokenId(uuid);
		
		userService.refreshToken(token_vo);
		
		log.info("================================= login response:\n" + gson.toJson(response));

		return ResponseEntity.status(HttpStatus.CREATED).body(response);// 201
	}

	// 회원가입
	@ExceptionHandler(CustomException.class)
	@PostMapping(value = "user/register", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "회원가입", notes = "회원가입 기능")
	public ResponseEntity<?> join(@RequestBody UserVO vo) throws Exception {

//		CustomException e = null;
//		CustomErrorCode errorCode = e.getErrorCode();

		// id 유효성 검사
		String id = userService.userId(vo); // 아이디 검색 결과
		if (!(id == null)) {// id가 있으면
			HttpStatus status = HttpStatus.BAD_REQUEST;
			Map<String, Object> map = new HashMap<>();
			map.put("status", HttpStatus.BAD_REQUEST);
			map.put("msg", "이미 존재하는 아이디 입니다.");
			map.put("id", id);
			log.info("================================= register response:\n" + gson.toJson(map));
			return new ResponseEntity<>(map, status);
//			HttpStatus status = HttpStatus.UNAUTHORIZED;
//	        String message = "이미 존재하는 아이디 입니다.";
//			return new ResponseEntity<>(message, status);
		}

		userService.join(vo);
//		StringBuffer msg = new StringBuffer();
//		msg.append("회원가입을 환영합니다.^^");
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.OK);
		map.put("msg", vo.getName()+"님, 회원가입을 환영합니다^^*");
		
		log.info("================================= register response:\n" + gson.toJson(map));
		
		return new ResponseEntity<>(map, status);
//		return ResponseEntity.ok().body(msg.toString());
	};

	@GetMapping(value = "user/refresh", produces = "application/json;charset=UTF-8")
	@ApiOperation(value = "로그인 인증", notes = "자동로그인이 가능하도록 사용자 token을 확인하는 기능")
	public ResponseEntity<String> getUserFromToken(@RequestHeader HttpHeaders headers) throws Exception {
		// 1)client에서 refreshToken을 header에 담아서 보내면 그 token을 꺼내서 사용한다
		String authToken = headers.getFirst("Authorization");
		if (authToken != null & authToken.startsWith("Bearer ")) {
			String refreshToken = null;
			refreshToken = authToken.substring(7);
//			System.out.println(refreshToken);
			// 2)client에서 받은 token을 받아서 token_vo에 담는다
			TokenVO token_vo = new TokenVO();
			token_vo.setRefreshToken(refreshToken);

			Jws<Claims> claims = jwtUtil.getClaims(refreshToken);// 코드 복호화+서명검증
			boolean isTokenValid = jwtUtil.validateToken(claims);// 토큰 만료시간 검증
			String id = jwtUtil.getKey(claims);// payload의 id를 취득
//	    	Object data = jwtUtil.getClaims(claims, key);//payload의 데이터 취득

			log.info("accessToken decryption : " + claims);
			log.info("accessToken due date : " + isTokenValid);
			log.info("payload id : " + id);
//	    	log.info("payload data 취득 : " + data);

//	    	
//	    	if(userService.refreshTokenChk(token_vo)!=null) {

			// 유효성 검사
//	    		DecodedJWT decodedJWT = JWT.decode(refreshToken);
//	    		Date date = new Date();
//	    		Date yom = decodedJWT.getExpiresAt();
//	    		//4-1)만약에 받은 token의 유효기간이 오늘까지이면 "기간이 만료되었습니다"를 return한다
//	    		if(decodedJWT.getExpiresAt().before(date)) {
//	    			
//	    			
//	    			return new ResponseEntity<>("기간이 만료되었습니다", HttpStatus.OK);
//	    		}

//	    		userService.refreshToken_delete(token_vo);

			String refreshId = userService.refreshTokenIdChk(id);
			if (refreshId != null) {
				userService.refreshTokenDelete(id);
			}

			// 4-2)token의 유효기간이 남아 있으면 refreshToken과 accessToken을 재발급한다
			UserVO vo = new UserVO();

			// 이미 있던걸 지우고 새로 만들어서 저장 (갱신)
			HashMap<String, String> map = new HashMap<String, String>() {
				{
					put("access", accessService.login(vo.getId(), vo.getPw()));
					put("refresh", refreshService.login(vo.getId(), vo.getPw()));
				}
			};

			// refreshToken 저장

			String re_refreshToken = refreshService.login(vo.getId(), vo.getPw());

			TokenVO re_token_vo = new TokenVO();
			Date today = new Date();

			token_vo.setId(id);
			token_vo.setRefreshToken(re_refreshToken);
			token_vo.setCreateDate(today);
			token_vo.setUpdateDate(today);
			String uuid = RandomStringUtils.random(36, true, true);
			token_vo.setRefreshTokenId(uuid);
			userService.refreshToken(token_vo);

//			HttpStatus status = HttpStatus.OK;
//			Map<String, Object> response = new HashMap<>();
//			response.put("status", HttpStatus.BAD_REQUEST);
//			response.put("msg", "로그인 되었습니다.");
//			return new ResponseEntity<>(response, status);

			return ResponseEntity.ok().body(map + "");
//	    	}else {	    		
//	    		//로그인 하라고 틩겨서
//	    		StringBuffer msg = new StringBuffer();
//				msg.append("Bearer 토큰이 필요합니다.");
//				return ResponseEntity.ok().body(msg.toString());
		}

//	    	return new ResponseEntity<>("refreshToken 요청을 받았습니다 = " + refreshToken + "\nBearer 토큰이 유효합니다.", HttpStatus.OK);
//	    }else {
//	    	return new ResponseEntity<>("Bearer 토큰이 필요합니다.", HttpStatus.UNAUTHORIZED);

//	    }
//	    HttpStatus status = HttpStatus.BAD_REQUEST;
//		Map<String, Object> map = new HashMap<>();
//		map.put("status", HttpStatus.BAD_REQUEST);
//		map.put("msg", "회원가입을 환영합니다~^^*");
//		return new ResponseEntity<>(map, status);
		return null;
	}

	@ApiOperation(value = "사용자 정보 조회", notes = "사용자에 대한 정보를 조회하는 기능")
	@GetMapping(value = "user/info", produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> getUserInfoToken(@RequestHeader HttpHeaders headers) throws Exception {
		String authToken = headers.getFirst("Authorization");
		if (authToken != null & authToken.startsWith("Bearer ")) {
			String refreshToken = null;
			refreshToken = authToken.substring(7);

			TokenVO token_vo = new TokenVO();
			token_vo.setRefreshToken(refreshToken);

			Jws<Claims> claims = jwtUtil.getClaims(refreshToken);// 코드 복호화+서명검증
			boolean isTokenValid = jwtUtil.validateToken(claims);// 토큰 만료시간 검증
			String id = jwtUtil.getKey(claims);// payload의 id를 취득

			UserInfoVO vo_id = userService.userInfo(id);
			HttpStatus status = HttpStatus.OK;
			Map<String, Object> map = new HashMap<>();
			map.put("userInfo", vo_id);
			map.put("msg", vo_id.getName() + "님의 정보를 조회합니다.");
			map.put("status", status);
			log.info("================================= userInfo response:\n" + gson.toJson(map));
			return ResponseEntity.ok(map);
		}
		return null;
	}

}
