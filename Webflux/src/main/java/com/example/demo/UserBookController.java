package com.example.demo;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.refresh.TokenVO;
import com.example.demo.userbook.UserBookService;
import com.example.demo.vo.SeatVO;
import com.example.demo.vo.UserBookVO;

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

	@Autowired
	private UserBookService userBookService;

	@ApiOperation(value = "사용자 항공편 예매 조회", notes = "마이페이지에서 사용자가 예매한 항공편을 조회하는 기능")
	@GetMapping(value = "flight/user-booking") // 마이페이지 예약목록
	public ResponseEntity<?> UserBookMypage(
			@RequestHeader HttpHeaders headers)
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
			String[] splitToken = accessToken.split("\\.");
			String payload = new String(Base64.getDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);
			JSONObject jsonObject = new JSONObject(payload);
			System.out.println(jsonObject);
			// user_id
			String id = jsonObject.getString("id");
			System.out.println(id);
			//////////////아이디 추출//////////////////////
			List<UserBookVO> list = userBookService.selectUserBook(id);
			HttpStatus status = HttpStatus.OK;
			Map<String, Object> response = new HashMap<>();
			response.put("reservationInfo", list);
			
			return new ResponseEntity<>(response, status);
				}
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String message = "항공편 예매를 조회할 수 없습니다.";
		return new ResponseEntity<>(message, status);
	}
			

	@ApiOperation(value = "남은 좌석 조회", notes = "항공편에 따른 남은 좌석을 조회하는 기능")
	@GetMapping(value = "/flight/extra-seat") // 남은 좌석정보
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
		return new ResponseEntity<>(response, status);
	}
	
	

	@ApiOperation(value = "항공편 예약", notes = "사용자가 항공편을 예약하는 기능")
	@PostMapping(value = "/flight/booking") // 마이페이지 예약목록
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
			String[] splitToken = accessToken.split("\\.");
			String payload = new String(Base64.getDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);
			JSONObject jsonObject = new JSONObject(payload);
			System.out.println(jsonObject);
			// user_id
			String id = jsonObject.getString("id");
			System.out.println(id);
			// id추출 완료----------------------------------------------------

//			남은좌석이 없을 시 예약할 수 없음			
			
			String personalStr = vo.getPersonal();
			int personalInt = Integer.parseInt(personalStr);
			
			String seatType = vo.getSeatType();
			
//			int economyCnt = userBookService.economyCnt(vo.getFlightId());
//			int prestigeCnt = userBookService.prestigeCnt(vo.getFlightId());
//			
//			int economyExtra = 6 - economyCnt - personalInt;
//			int prestigeExtra = 4 - prestigeCnt - personalInt;
//			
//			if(seatType.equals("economy")) {
//				if(economyExtra == 0) {
//					HttpStatus status = HttpStatus.BAD_REQUEST;
//					String message = "economy 좌석이 남아있지 않아 예약할 수 없습니다.";
//					return new ResponseEntity<>(message, status);
//				}
//				
//			}else{
//				if(prestigeExtra == 0) {
//					HttpStatus status = HttpStatus.BAD_REQUEST;
//					String message = "prestige 좌석이 남아있지 않아 예약할 수 없습니다.";
//					return new ResponseEntity<>(message, status);
//				}
//			}
//			
			vo.setUserId(id);
			String uuid = RandomStringUtils.random(36, true, true);
			vo.setReservationId(uuid);
			vo.setPersonal(vo.getPersonal());
			vo.setFlightId(vo.getFlightId());
			vo.setSeatType(vo.getSeatType());
			Date createDate = new Date(new java.util.Date().getTime()); // java.util.Date 객체를 java.sql.Date 객체로 변환
			vo.setCreateDate(createDate);
			
			int economyCharge = userBookService.FlightInfo(vo.getFlightId()).getEconomyCharge();
			int prestigeCharge = userBookService.FlightInfo(vo.getFlightId()).getPrestigeCharge();
//			String seatType = vo.getSeatType();
//			String personalStr = vo.getPersonal();
//			int personalInt = Integer.parseInt(personalStr);
			
			if(seatType.equals("economy")) {
				vo.setChargeSum(personalInt*economyCharge);
			}else {
				vo.setChargeSum(personalInt*prestigeCharge);
			}
				
			
			
			System.out.println(userBookService.UserInfo(id));
			userBookService.insertUserBook(vo);
			HttpStatus status = HttpStatus.OK;
			String message = "저장되었습니다.";
			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("reservationInfo", vo);
			response.put("userInfo", userBookService.UserInfo(id));
			response.put("flightInfo", userBookService.FlightInfo(vo.getFlightId()));
			
			return new ResponseEntity<>(response, status);
				}
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String message = "항공편예약에 실패하였습니다.";
		return new ResponseEntity<>(message, status);
	}
	

}