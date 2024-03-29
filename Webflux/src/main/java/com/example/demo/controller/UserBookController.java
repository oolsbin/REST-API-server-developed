package com.example.demo.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserBookService;
import com.example.demo.token.JwtUtil;
import com.example.demo.vo.FlightVO;
import com.example.demo.vo.SeatVO;
import com.example.demo.vo.TokenVO;
import com.example.demo.vo.UserBookVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 예매 클래스
@Api(tags = "항공편예매")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequiredArgsConstructor//쓰는이유가 뭐지
@Slf4j
public class UserBookController {
//@RequestHeader HttpHeaders headers

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Autowired
	private UserBookService userBookService;
	
	@Autowired
    private JwtUtil jwtUtil;

	@ApiOperation(value = "사용자 항공편 예매 조회", notes = "마이페이지에서 사용자가 예매한 항공편을 조회하는 기능")
	@GetMapping(value = "flight/user-booking", produces="application/json;charset=UTF-8") // 마이페이지 예약목록
	public ResponseEntity<?> UserBookMypage(
			@RequestHeader HttpHeaders headers)
			throws Exception {
		// 헤더토큰 꺼내온다
		String authToken = headers.getFirst("Authorization");
		if (authToken != null & authToken.startsWith("Bearer ")) {
			String accessToken = null;
			accessToken = authToken.substring(7);

//			System.out.println(accessToken);
//			TokenVO token_vo = new TokenVO();
//			token_vo.setAccessToken(accessToken);
			// accessToken 사용자정보 꺼내기 (id값)
			
			Jws<Claims> claims = jwtUtil.getClaims(accessToken);//코드 복호화+서명검증
	    	boolean isTokenValid = jwtUtil.validateToken(claims);//토큰 만료시간 검증
	    	String id = jwtUtil.getKey(claims);//payload의 id를 취득
	    	
	    	log.info("token 복호화 : " + claims);
	    	log.info("토큰만료시간 : " + isTokenValid);
	    	log.info("payload id 취득 : " + id);
			
			//////////////아이디 추출//////////////////////
			List<UserBookVO> list = userBookService.selectUserBook(id);
			HttpStatus status = HttpStatus.OK;
			Map<String, Object> response = new HashMap<>();
			if(list.isEmpty()) {
				response.put("reservationInfo", list);
			}else {
				response.put("reservationInfo", list);
			}
			
			response.put("status", status);
			response.put("msg", "사용자가 예매한 항공편을 조회합니다.");
			log.info("================================= flight-mypage response:\n" + gson.toJson(response));
			return new ResponseEntity<>(response, status);
				}
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.BAD_REQUEST);//400
		map.put("msg", "항공편 예매를 조회할 수 없습니다.");
		log.info("================================= flight userBook response:\n" + gson.toJson(map));
		return new ResponseEntity<>(map, status);
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		String message = "항공편 예매를 조회할 수 없습니다.";
//		return new ResponseEntity<>(message, status);
	}
			

	@ApiOperation(value = "남은 좌석 조회", notes = "항공편에 따른 남은 좌석을 조회하는 기능")
	@GetMapping(value = "flight/extra-seat", produces="application/json;charset=UTF-8") // 남은 좌석정보
	public ResponseEntity<?> UserBookCnt(
			@ApiParam(value = "항공편아이디", required = true, example = "0FDBXyQ9JZkUZMkCKtSAjtgmZeCaIbOpe4TO")
			@RequestParam String flightId) throws Exception {

		int economyCnt = userBookService.economyCnt(flightId);
		int prestigeCnt = userBookService.prestigeCnt(flightId);
		
		int economyExtra = 6 - economyCnt;
		int prestigeExtra = 4 - prestigeCnt;
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> response = new HashMap<>();
		response.put("economyExtra", economyExtra);
		response.put("prestigeExtra", prestigeExtra);
		log.info("================================= flight extra-seat response:\n" + gson.toJson(response));
		return new ResponseEntity<>(response, status);
	}
	
	

	@ApiOperation(value = "항공편 예약", notes = "사용자가 항공편을 예약하는 기능")
	@PostMapping(value = "flight/booking", produces="application/json;charset=UTF-8") // 마이페이지 예약목록
	public ResponseEntity<?> UserBookInsert(@RequestHeader HttpHeaders headers,
			@RequestBody SeatVO vo)
			throws Exception {
		// 헤더토큰 꺼내온다
		String authToken = headers.getFirst("Authorization");
		if (authToken != null & authToken.startsWith("Bearer ")) {
			String accessToken = null;
			accessToken = authToken.substring(7);

			System.out.println(accessToken);
			TokenVO token_vo = new TokenVO();
			token_vo.setAccessToken(accessToken);
			// accessToken 사용자정보 꺼내기 (id값)
//			String[] splitToken = accessToken.split("\\.");
//			String payload = new String(Base64.getDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);
//			JSONObject jsonObject = new JSONObject(payload);
//			System.out.println(jsonObject);
//			// user_id
//			String id = jsonObject.getString("id");
//			System.out.println(id);
			
			Jws<Claims> claims = jwtUtil.getClaims(accessToken);//코드 복호화+서명검증
	    	boolean isTokenValid = jwtUtil.validateToken(claims);//토큰 만료시간 검증
	    	String id = jwtUtil.getKey(claims);//payload의 id를 취득
	    	
	    	log.info("token 복호화 : " + claims);
	    	log.info("토큰만료시간 : " + isTokenValid);
	    	log.info("payload id 취득 : " + id);
			
			// id추출 완료----------------------------------------------------

//			남은좌석이 없을 시 예약할 수 없음			
			
			String personalStr = vo.getPersonal();
			int personalInt = Integer.parseInt(personalStr);
			
			String seatType = vo.getSeatType();
			
			int economyCnt = userBookService.economyCnt(vo.getFlightId());
			int prestigeCnt = userBookService.prestigeCnt(vo.getFlightId());
			
			int economyExtra = 6 - economyCnt - personalInt;
			int prestigeExtra = 4 - prestigeCnt - personalInt;
			
			if(seatType.equals("economy")) {
			    if(economyExtra == 0 || economyExtra < 0) {
			    	HttpStatus status = HttpStatus.OK;
					Map<String, Object> map = new HashMap<>();
					map.put("status", HttpStatus.BAD_REQUEST);
					map.put("msg", "economy 좌석이 남아있지 않아 예약할 수 없습니다.");
					log.info("================================= flight-userBook response:\n" + gson.toJson(map));
					return new ResponseEntity<>(map, status);
//			        HttpStatus status = HttpStatus.BAD_REQUEST;
//			        String message = "economy 좌석이 남아있지 않아 예약할 수 없습니다.";
//			        return new ResponseEntity<>(message, status);
			    }
			} else if(seatType.equals("prestige")) {
			    if(prestigeExtra == 0 || prestigeExtra < 0) {
			    	HttpStatus status = HttpStatus.OK;
					Map<String, Object> map = new HashMap<>();
					map.put("status", HttpStatus.BAD_REQUEST);
					map.put("msg", "prestige 좌석이 남아있지 않아 예약할 수 없습니다.");
					log.info("================================= flight-userBook response:\n" + gson.toJson(map));
					
					return new ResponseEntity<>(map, status);
//			        HttpStatus status = HttpStatus.BAD_REQUEST;
//			        String message = "prestige 좌석이 남아있지 않아 예약할 수 없습니다.";
//			        return new ResponseEntity<>(message, status);
			    }
			}
			
			vo.setUserId(id);
			String uuid = RandomStringUtils.random(36, true, true);
			vo.setReservationId(uuid);
			vo.setPersonal(vo.getPersonal());
			vo.setFlightId(vo.getFlightId());
			vo.setSeatType(vo.getSeatType());
			Date createDate = new Date(new java.util.Date().getTime()); // java.util.Date 객체를 java.sql.Date 객체로 변환
			vo.setCreateDate(createDate);
			
			int economyCharge = userBookService.flightInfo(vo.getFlightId()).getEconomyCharge();
			int prestigeCharge = userBookService.flightInfo(vo.getFlightId()).getPrestigeCharge();
//			String seatType = vo.getSeatType();
//			String personalStr = vo.getPersonal();
//			int personalInt = Integer.parseInt(personalStr);
			
			if(seatType.equals("economy")) {
				vo.setChargeSum(personalInt*economyCharge);
			}else {
				vo.setChargeSum(personalInt*prestigeCharge);
			}
				
			
			
			System.out.println(userBookService.userInfo(id));
			userBookService.insertUserBook(vo);
			HttpStatus status = HttpStatus.OK;
			String message = "항공편 예약이 완료되었습니다.";
			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("status", status);
			response.put("reservationInfo", vo);
			response.put("userInfo", userBookService.userInfo(id));
			response.put("flightInfo", userBookService.flightInfo(vo.getFlightId()));
		
			log.info("================================= flight-userBook response:\n" + gson.toJson(response));
			return new ResponseEntity<>(response, status);
				}
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> map = new HashMap<>();
		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("msg", "항공편예약에 실패하였습니다.");
		log.info("================================= flight-userBook response:\n" +gson.toJson(map));
		return new ResponseEntity<>(map, status);
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//		String message = "항공편예약에 실패하였습니다.";
//		return new ResponseEntity<>(message, status);
	}
	
	//예매취소
	@DeleteMapping(value = "flight/cancellation")
	public ResponseEntity<?> userBookDelete(@RequestParam String reservationId) throws Exception {
		int test = userBookService.deleteUserBook(reservationId);
		if(test==0) {
			HttpStatus status = HttpStatus.OK;
			String message = "이미 취소된 항공편 입니다.";
			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("status", status);
			response.put("data", test);
			log.info("================================= flight-userBook response:\n" + gson.toJson(response));
			return ResponseEntity.ok().body(response);
		}
		
		HttpStatus status = HttpStatus.OK;
		String message = "항공편 예약이 취소되었습니다.";
		Map<String, Object> response = new HashMap<>();
		response.put("message", message);
		response.put("status", status);
		response.put("data", test);
		log.info("================================= flight-userBook response:\n" + gson.toJson(response));
		return ResponseEntity.ok().body(response);
	}
	

}