package com.example.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.example.demo.flight.FlightVO;
import com.example.demo.refresh.TokenVO;
import com.example.demo.token.JwtAccessService;
import com.example.demo.token.JwtRefreshService;
import com.example.demo.user.UserService;
import com.example.demo.user.UserVO;
import com.example.demo.userbook.UserBookService;
import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.SeatVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.json.JSONObject;

import ch.qos.logback.core.status.Status;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.lang.Integer;

// 예매 클래스
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserBookController {
//@RequestHeader HttpHeaders headers

	@Autowired
	private UserBookService userBookService;

	@GetMapping(value = "/userBookList") // 마이페이지 예약목록
	public ResponseEntity<?> UserBookSelect(@RequestParam UserBookVO vo) throws Exception {
		List<UserBookVO> list = userBookService.selectUserBook(vo);
		System.out.println("userBookList" + list);
		return ResponseEntity.ok().body(list);
	}
	
	

	@GetMapping(value = "/flight/extra-seat") // 남은 좌석정보
	public ResponseEntity<?> UserBookCnt(@RequestParam String flightId) throws Exception {

		int economyCnt = userBookService.economyCnt(flightId);
		int prestigeCnt = userBookService.prestigeCnt(flightId);
//			    if (prestigeCnt == 0) {
//			        return 0; // 기본값으로 0을 반환하도록 수정
//			    }
// DB 조회 결과 값을 반환
		
		
		int economyExtra = 6 - economyCnt;
		int prestigeExtra = 4 - prestigeCnt;
		
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> response = new HashMap<>();
		response.put("economyExtra", economyExtra);
		response.put("prestigeExtra", prestigeExtra);
		return new ResponseEntity<>(response, status);
	}
	
	

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
//			int economyCnt = userBookService.economyCnt(vo.getFlightId());
//			System.out.println("economyCnt = " + economyCnt);
//			int prestigeCnt = userBookService.prestigeCnt();
//			System.out.println("prestigeCnt = " + prestigeCnt);
			
//			int economyCntAll = economyCnt + vo.getPersonal();
//			String prestigeCntAll;
//			prestigeCntAll = prestigeCnt + vo.getPersonal();
//			
//			if(!(economyCnt<7 || prestigeCnt<5)) {//정상범위 내에 있지 않으면
//				HttpStatus status = HttpStatus.BAD_REQUEST;
//				String message = "예약할 수 없습니다.";
//				Map<String, Object> response = new HashMap<>();
//				response.put("message", message);
//				return new ResponseEntity<>(response, status);
//			}
			
			vo.setUserId(id);
			String uuid = RandomStringUtils.random(36, true, true);
			vo.setReservationId(uuid);
			vo.setPersonal(vo.getPersonal());
			vo.setFlightId(vo.getFlightId());
			vo.setSeatType(vo.getSeatType());
			
//			userBookService.UserInfo();
			userBookService.insertUserBook(vo);
			HttpStatus status = HttpStatus.OK;
			String message = "저장되었습니다.";
			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("vo", vo);
//			response.put("", );
			return new ResponseEntity<>(response, status);
				}
		return null;
	}
	

}

//
////	static Scanner sc = new Scanner(System.in);
////	static String id;
////	static String pass;
////
////	Socket s;
////	static DataInputStream dis;
////	static DataOutputStream dos;
////	static Date date;
////	// 회원용 DB controller
////	static DBClientController dbsc = new DBClientController();
////	// 운영자용 DB controller
////	static DBAdminController dbac = new DBAdminController();
//
//	// 예매 관련 메소드
//	public ResponseEntity<?> bookAirport(Socket s) {
//		int cnt = 0;
//		// 예매할 번호를 입력받을 num
//		int num = 0;
//
//		while (true) {
//			try {
//				dos = new DataOutputStream(s.getOutputStream());
//
//				// 오늘 날짜를 구한다. (3월 7일 기준)
//				Calendar cal = new GregorianCalendar(Locale.KOREA);
//				// 오늘 날짜를 구한 뒤,
//				cal.setTime(new Date());
//				// 4일을 더한다. (3월 3일 -> 3월 7일 테스트용. 나중에 시연할때는 없애버리면 됨.)
//				cal.add(Calendar.DAY_OF_YEAR, 2);
//
//				HashSet<Integer> numSet = dbsc.selectAllCountry(cal);
//				// 모든 스케줄 리스트의 번호를 numSet에 담는다.
//
//
//				System.out.print("원하시는 번호를 입력하세요 : ");
//				num = new Scanner(System.in).nextInt();
//
//				if (!numSet.contains(num) || num == 0) {
//					System.out.println("리스트 안의 번호를 입력하세요.");
//					continue;
//				}
//
//				// 리스트 번호를 입력하여 해당 스케줄의 정보를 가져온다
//				dbsc.selectCountryFromCNUM(num);
//
//				// 현재 날짜 이후의 DB list를 불러온다.
//				System.out.println("\n================= 좌석도 =============================");
//				ArrayList<String> sList = dbsc.selectAllSeat(num);
//				System.out.println("==================================================");
//
//				System.out.println("원하는 좌석을 고르세요.");
//				String seat = new Scanner(System.in).nextLine();
//
//				if (!sList.contains(seat)) {
//					System.out.println("리스트 안의 좌석에서 고르세요.");
//					continue;
//				}
//
//				// 좌석 정보를 update한다.
//				dbsc.updateSeat(seat, num);
//
//				// 현재 회원정보를 조회한다.
//				System.out.println("========= 현재 예매 회원 정보 =========");
//				dbac.selecyByPnum(ClientDTO.getInstance().getpNum());
//
//				// 예매한 회원 정보를 Board Table에 넣기. -> 예매하기
//				dbsc.insertBoardTable();
//				System.out.println("======== 현재 예매 회원의 탑승권 정보 ========");
//				dbsc.printBoardTable(ClientDTO.getInstance().getpNum());
//				// dos.writeInt(num);
//
//				
//				System.out.println("============ 예매종료 ============");
//				// dos.writeUTF("접속종료");
//				
//				BoardingDTO.getInstance().setpNum(ClientDTO.getInstance().getpNum());
//				// BoardingDTO에 여권번호 정보를 넣는다.
//				
//				dos.writeUTF(ClientDTO.getInstance().getpName() + "님이 " + BoardingDTO.getInstance().getbStartSite() +"에서 예매 완료하였습니다.");
//
//				break;
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
//		}
//	}
