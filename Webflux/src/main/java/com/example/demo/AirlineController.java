package com.example.demo;

import java.io.IOException;
import java.net.URI;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.airline.AirlineService;
import com.example.demo.airline.AirlineVO;
import com.example.demo.dto.airlinedto.ListVO;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
public class AirlineController {

	@Autowired
	private AirlineService airlineService;
	
	// 공항목록조회(airportId:공항ID, airportNm:공항명)
		@GetMapping(value = "/airline")
		@Operation(summary = "항공사 목록 조회", description = "국내 항공사의 목록을 조회하는 기능")
		//HttpEntity = HttpHeader와 HttpBody를 포함하는 클래스
		//HttpEntity를 상속받아 구현한 클래스가 RequestEntity, ResponseEntity이다.
		//ResponseEntity는 HttpRequest에 대한 응답 데이터를 포함하는 클래스로 HttpStatus, HttpHeaders, HttpBody를 포함한다.
		public ResponseEntity<?> airline
			   (@RequestParam(value = "airlineId", required = false) String airlineId,
				@RequestParam(value = "airlineNm", required = false) String airlineNm)
				throws IOException, ParseException {

			//RestTemplate 사용하여 파싱
			try {
				//1.RestTemplate 객체를 생성한다.
				RestTemplate restTemplate = new RestTemplate();
				//2.HttpHeaders 객체를 생성한다.
				HttpHeaders headers = new HttpHeaders();
				//3.Json형태의 Response를 받는다.
				headers.add("Content-Type", "application/json");
//				headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
				//4.headers를 담는다.
				HttpEntity<?> entity = new HttpEntity<>(headers);
				
				
				
				//1. string 방식
				String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList?"
						+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
						+ "&airlineId=" + airlineId + "&airlineNm=" + airlineNm + "&_type=json";
				
				URI uri = new URI(urlBuilder);

				
				final ResponseEntity<?> response = restTemplate
						.exchange(uri
								, HttpMethod.GET //request method
								, entity	//http entity
								, ListVO.class); //리턴받을 String타입 클래스
				
				
				

				if (response.getStatusCodeValue() == 200) {
					return ResponseEntity.ok(response.getBody());
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
	
	//db 저장
	@PostMapping("/airline-create")
	public ResponseEntity<?> insertAirline(@RequestBody AirlineVO vo) throws Exception {
		if(airlineService.insertAirline(vo)!=1) {
		StringBuffer msg = new StringBuffer();
		msg.append("저장안됨");
		return ResponseEntity.ok().body(msg);
		};
		
		StringBuffer msg = new StringBuffer();
		msg.append("저장됨");
		return ResponseEntity.ok().body(msg);
	}
	
	//db 조회
	@RequestMapping(value="/airline-read")
	public ResponseEntity<?> selectAirline(@RequestBody AirlineVO vo) throws Exception{
//		if(airlineService.selectAirline(vo) != null) {
		AirlineVO test = airlineService.selectAirline(vo);
		System.out.println("조회테스트" + test);
		return ResponseEntity.ok().body(test);
//		}
	}

	//db 수정
	@RequestMapping(value="/airline-update")
	public ResponseEntity<?> update(@RequestBody AirlineVO vo) throws Exception{
		airlineService.updateAirline(vo);
		StringBuffer msg = new StringBuffer();
		msg.append("수정됨");
		return ResponseEntity.ok().body(msg);
	}
	
	//db 삭제
	@RequestMapping("/airline-delete")
	public ResponseEntity<?> delete(@RequestBody AirlineVO vo) throws Exception{
		airlineService.deleteAirline(vo);
		StringBuffer msg = new StringBuffer();
		msg.append("삭제");
		return ResponseEntity.ok().body(msg);
	}
}
