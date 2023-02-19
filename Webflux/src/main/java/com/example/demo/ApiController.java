package com.example.demo;

import java.io.BufferedReader;
import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.CharacterCodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.xml.sax.SAXException;

import com.example.demo.airport.AirportService;
import com.example.demo.dto.AirportVO;
import com.example.demo.dto.FilghtVO;
import com.example.demo.dto.ItemVO;
import com.example.demo.dto.ListVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ch.qos.logback.classic.Logger;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Model;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//swagger-ui 타이틀 이름과 설명
@Tag(name = "air Controller", description = "항공관련 컨트롤러") // 스프링 독
@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiController {

	
	
	// 공항목록조회(airportId:공항ID, airportNm:공항명)
	@GetMapping(value = "/airline_find")//, produces = "application/json"
	@Operation(summary = "항공사 목록 조회", description = "국내 항공사의 목록을 조회하는 기능")
	//HttpEntity = HttpHeader와 HttpBody를 포함하는 클래스
	//HttpEntity를 상속받아 구현한 클래스가 RequestEntity, ResponseEntity이다.
	//ResponseEntity는 HttpRequest에 대한 응답 데이터를 포함하는 클래스로 HttpStatus, HttpHeaders, HttpBody를 포함한다.
	public ResponseEntity<ListVO> callapihttp
		   (@RequestParam(value = "항공사ID", required = false) String airlineId,
			@RequestParam(value = "항공사명", required = false) String airlineNm)
			throws IOException, ParseException {

		//RestTemplate 사용하여 파싱
		try {
			//1.RestTemplate 객체를 생성한다.
			RestTemplate restTemplate = new RestTemplate();
			//2.HttpHeaders 객체를 생성한다.
			HttpHeaders headers = new HttpHeaders();
			//3.Json형태의 Response를 받는다.
			headers.add("Content-Type", "application/json");
//			headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
			//4.headers를 담는다.
			HttpEntity<ListVO> entity = new HttpEntity<>(headers);
			
			//1. string 방식
			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&airlineId=" + airlineId + "&airlineNm=" + airlineNm + "&_type=json";

			//2. fromUri를 통한 queryparam 방식
			
//			UriComponentsBuilder builder = UriComponentsBuilder.fromUri(
//					URI.create("http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList"))
//					.queryParam("serviceKey", "s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D")
//					.queryParam("airlineId", airlineId)
//					.queryParam("airlineNm", airlineNm)
//					.queryParam("_type", "json");
			
			//3. fromHttpUrl
//			String uri = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList")
//					.queryParam("serviceKey", "s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D")
////					.queryParam("airlineId", airlineId)
////					.queryParam("airlineNm", airlineNm)
//					.queryParam("_type", "json")
//					.build().toString();
			
			final ResponseEntity<ListVO> response = restTemplate
					.exchange(urlBuilder.toString()
							, HttpMethod.GET //request method
							, entity	//http entity
							, ListVO.class); //리턴받을 String타입 클래스

			if (response.getStatusCodeValue() == 200) {
				return response;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
		@GetMapping(value = "/airport_find")
		@Operation(summary = "공항 목록 조회", description = "국내 공항 목록을 조회하는 기능")
		public ResponseEntity<AirportVO> AirportSelect
			   (@RequestParam(value = "항공사ID", required = false) String airportId,
				@RequestParam(value = "항공사명", required = false) String airportNm)
				throws IOException, ParseException {

			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				HttpEntity<AirportVO> entity = new HttpEntity<>(headers);

				String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getArprtList?"
						+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
						+ "&airportId=" + airportId + "&airportNm=" + airportNm + "&_type=json";
				

				final ResponseEntity<AirportVO> response = restTemplate
						.exchange(urlBuilder.toString()
								, HttpMethod.GET
								, entity
								, AirportVO.class);

				if (response.getStatusCodeValue() == 200) {
					return response;//??
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		
		
		@GetMapping(value = "/Filght_find")
		@Operation(summary = "항공운항정보 목록 조회", description = "출/도착지를 기준으로 국내선 항공운항정보 목록을 조회하는 기능")
		public ResponseEntity<FilghtVO> FilghtSelect
			   (@RequestParam(value = "출발공항ID", required = false) String depAirportId,
			    @RequestParam(value = "도착공항ID", required = false) String arrAirportId,
			    @RequestParam(value = "출발일(YYYYMMDD)", required = false) String depPlandTime,
				@RequestParam(value = "항공사ID", required = false) String airlineId)
				throws IOException, ParseException {

			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				HttpEntity<AirportVO> entity = new HttpEntity<>(headers);

				String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?"
						+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
						+ "&depAirportId=" + "NAARKJJ" + "&arrAirportId=" + "NAARKPC" + "&depPlandTime=" + "20211201" + "&airlineId=" + "AAR" + "&numOfRows=" + "10" + "&pageNo=" + "1" + "&_type=xml";
				

				final ResponseEntity<FilghtVO> response = restTemplate
						.exchange(urlBuilder.toString()
								, HttpMethod.GET
								, entity
								, FilghtVO.class);

				if (response.getStatusCodeValue() == 200) {
					return response;//??
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		
		
	
    
//    // 로그인
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> user) {
//        User member = userRepository.findByEmail(user.get("email"))
//                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
//        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        return JwtTokenProvider.createToken(member.getUsername(), member.getRoles());
//    }
    

//	@GetMapping("/apitest2")
//	public String callapihttp2() {
//
//		StringBuffer result = new StringBuffer();
//
//		try {
//			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?"
//					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
//					+ "&depAirportId=NAARKJJ" + "&arrAirportId=NAARKPC" + "&depPlandTime=20211201"
//					+ "&airlineId=AAR&numOfRows=10" + "&pageNo=1" + "&_type=json";
//			URL url = new URL(urlBuilder);
//			// 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			// 5. 통신을 위한 메소드 SET
//			conn.setRequestMethod("GET");
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//
//			String returnLine;
//			
//			while ((returnLine = br.readLine()) != null) {
//				result.append(returnLine + "/n");
//			}
//			conn.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result + "";
//	}
}