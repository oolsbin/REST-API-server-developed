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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.collections.MapUtils;
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



// 예매 클래스
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserBookController {
//@RequestHeader HttpHeaders headers

	@Autowired
	private UserBookService userBookService;
	
	@GetMapping(value = "/userBookList")//마이페이지 예약목록
	public ResponseEntity<?> UserBookSelect(@RequestBody UserBookVO vo) throws Exception {
		List<UserBookVO> list = userBookService.selectUserBook(vo);
		System.out.println("userBookList" + list);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping(value = "/seatCnt")//마이페이지 예약목록
	public ResponseEntity<?> UserBookSelect(
			@RequestHeader HttpHeaders headers,
			@RequestBody SeatVO vo) throws Exception {
		//헤더토큰 꺼내온다
		String authToken = headers.getFirst("Authorization");
	    if(authToken != null & authToken.startsWith("Bearer ")) {
	    	String accessToken = null;
	    	accessToken = authToken.substring(7);
	    	
	    	System.out.println(accessToken);
	    	TokenVO token_vo = new TokenVO();
	    	token_vo.setAccessToken(accessToken);
	    	//accessToken 사용자정보 꺼내기 (id값)
	    	String[] splitToken = accessToken.split("\\.");
	    	String payload = new String(Base64.getDecoder().decode(splitToken[1]), StandardCharsets.UTF_8);
	    	JSONObject jsonObject = new JSONObject(payload);
	    	System.out.println(jsonObject);
	    	//user_id
	    	String id = jsonObject.getString("id");
	    	System.out.println(id);
    	//id추출 완료----------------------------------------------------
	    	vo.setUserId(id);
	    	
		List<SeatVO> list = userBookService.seatList(vo);
		System.out.println("userBookList" + list);
//		int seatCount = Collections.frequency(list, vo.getFlightId());
		int seatCount = list.size();
		
		int economyCnt = userBookService.economyCnt();
		int prestigeCnt = userBookService.prestigeCnt();
		
		if(!vo.getSeatType().equals("prestige")) {
			int cnt = Integer.parseInt(vo.getPersonal());
			if(economyCnt + cnt >6) {
			HttpStatus status = HttpStatus.NOT_FOUND;
	        String message = "이코노미좌석이 초과하였습니다.";
			return new ResponseEntity<>(message, status);
			}else {
//			// 요청 헤더에서 추출한 사용자 ID를 mybatis 매퍼에 전달하여 SQL 쿼리에서 사용할 수 있도록
//	        userBookService.insertUserBook(vo);
			//2) personal에 따른 insert문 반복실행
			for (int ii = 0; ii < cnt; ii++) {
			    //1)좌석값 번호의 누적값 세팅
			    long sum = ii + 1;
			    System.out.println("누적 값: " + sum);
			    //좌석저장
			    vo.setSeatType("E" + sum); //E뒤에 누적값으로 set
			    userBookService.insertUserBook(vo);
			}
			HttpStatus status = HttpStatus.OK;
			int economyExtra = 7 - economyCnt - cnt;
			String message = "현재 이코노미좌석이 " + economyExtra + " 남았습니다.";
			Map<String, Object> response = new HashMap<>();
			response.put("message", message);
			response.put("economyExtra", economyExtra);
			return new ResponseEntity<>(response, status);
			}
		}else if(!vo.getSeatType().equals("economy")) {
			int cnt = Integer.parseInt(vo.getPersonal());
			if(prestigeCnt + cnt >4) {
				HttpStatus status = HttpStatus.NOT_FOUND;
		        String message = "비즈니스좌석이 초과하였습니다.";
				return new ResponseEntity<>(message, status);
			}else {
				for (int ii = 0; ii < cnt; ii++) {
				    //1)좌석값 번호의 누적값 세팅
				    long sum = ii + 1;
				    System.out.println("누적 값: " + sum);
				    //좌석저장
				    vo.setSeatType("B" + sum); //B뒤에 누적값으로 set
				    userBookService.insertUserBook(vo);
				}
				HttpStatus status = HttpStatus.OK;
		        int prestigeExtra = 7 - economyCnt - cnt;
		        String message = "현재 비즈니스좌석이 " + prestigeExtra + " 남았습니다.";
				Map<String, Object> response = new HashMap<>();
				response.put("message", message);
				response.put("prestigeExtra", prestigeExtra);
		        return new ResponseEntity<>(message, status);
			}
		}
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

