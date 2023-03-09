package com.example.demo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.flightdto.ItemVO;
import com.example.demo.dto.flightdto.ListVO;
import com.example.demo.flight.CountVO;
import com.example.demo.flight.FlightService;
import com.example.demo.flight.FlightVO;
import com.example.demo.flight.page.SearchDto;
import com.example.demo.mapper.FlightMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import io.jsonwebtoken.lang.Arrays;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
//swagger-ui 타이틀 이름과 설명
@Tag(name = "air Controller", description = "항공관련 컨트롤러") // 스프링 독
@RestController
//@RequiredArgsConstructor
@Slf4j
public class ApiController {
	
	@Autowired
	private FlightMapper flightmapper;


	@Autowired
	private FlightService flightService;
	
	Gson gson = new Gson();
	
	
	// 공항목록조회(airportId:공항ID, airportNm:공항명)
	@GetMapping(value = "/airline")//, produces = "application/json"
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
//			headers.set("accept", MediaType.APPLICATION_JSON_VALUE);
			//4.headers를 담는다.
			HttpEntity<?> entity = new HttpEntity<>(headers);
			
			
			
			//1. string 방식
			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getAirmanList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&airlineId=" + airlineId + "&airlineNm=" + airlineNm + "&_type=json";
			
			URI uri = new URI(urlBuilder);
			
		
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
	
	
	

		

		//total count
		@GetMapping(value = "/flight")
		@Operation(summary = "항공운항정보 목록 조회", description = "출/도착지를 기준으로 국내선 항공운항정보 목록을 조회하는 기능")
		public ResponseEntity<?> flightSelect
			   (@RequestParam(value = "depAirportId", required = false) String depAirportId,
			    @RequestParam(value = "arrAirportId", required = false) String arrAirportId,
			    @RequestParam(value = "depPlandTime", required = false) String depPlandTime,
				@RequestParam(value = "airlineId", required = false) String airlineId,
				@RequestParam(value = "numOfRows", required = false) Integer numOfRows,//시리얼라이져가 되서 보내주기 때문에 ㄱㅊ
				@RequestParam(value = "pageNo", required = false) Integer pageNo			
				)throws IOException, ParseException {

			try {
				RestTemplate restTemplate = new RestTemplate();
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "application/json");
				HttpEntity<?> entity = new HttpEntity<>(headers);

				String urlBuilder = "https://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?"
						+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
						+ "&depAirportId=" + depAirportId + "&arrAirportId=" + arrAirportId + "&depPlandTime=" + depPlandTime + "&airlineId=" + airlineId 
						+ "&numOfRows=" + numOfRows + "&pageNo=" + pageNo +"&_type=json";

				URI uri = new URI(urlBuilder);
				
				final ResponseEntity<String> response;
				try {
					response = restTemplate
						.exchange(uri
								, HttpMethod.GET
								, entity
								, String.class);
				
				System.out.println(response);
				}catch(RestClientException e){
					e.printStackTrace();
					return ResponseEntity.notFound().build();
				}
				
				JsonElement je = gson.fromJson(response.getBody(), JsonElement.class);
				
				if (!je.isJsonObject()) {
					return ResponseEntity.notFound().build();
				}
				
				if (!je.getAsJsonObject().get("response").isJsonObject()) {
					return ResponseEntity.notFound().build();
				}
				
				if (!je.getAsJsonObject().get("response").getAsJsonObject().get("body").isJsonObject()) {
					return ResponseEntity.notFound().build();
				}
				
				if (!je.getAsJsonObject().get("response").getAsJsonObject().get("body").getAsJsonObject().get("items").isJsonObject()) {
					return ResponseEntity.notFound().build();
				}
				
				JsonElement itemJe = je.getAsJsonObject().get("response").getAsJsonObject().get("body").getAsJsonObject().get("items").getAsJsonObject().get("item");
				Integer totalCount = je.getAsJsonObject().get("response").getAsJsonObject().get("body").getAsJsonObject().get("totalCount").getAsInt();
				
				List<ItemVO> parsedItemVOs = new ArrayList<>();
				
				if (itemJe.isJsonPrimitive()) {
					return ResponseEntity.notFound().build();
				} else if (itemJe.isJsonObject()) {
					ItemVO stringItem = gson.fromJson(itemJe, ItemVO.class);
					parsedItemVOs.add(stringItem);
				} else if (itemJe.isJsonArray()) {
					Class<List<ItemVO>> type = (Class<List<ItemVO>>) new ArrayList<ItemVO>().getClass();
					parsedItemVOs.addAll(gson.fromJson(itemJe, type));
				}
				
//////
				
				if (!response.getStatusCode().is2xxSuccessful()) {	//status 코드
					throw new Exception();
					
				}
				
				//
//				ListVO responsebody = response.getBody();
				
				//api 데이터
//				String itemList = responsebody.getResponse().getBody().getItems().getItem();
//				List<ItemVO> itemList = responsebody.getResponse().getBody().getItems().getItem();
				
//////
				
//				int totalCount = 0;
//				totalCount = itemList.size();
//
//				FlightVO fligtVo = new FlightVO();
//				flightService.total();
//				fligtVo.setTotalcount(itemList.size());
				
				
				Map<String, Object> map = new HashMap<>();
				map.put("pageNo", (pageNo-1) * numOfRows);
				map.put("numOfRows", numOfRows);
				List<FlightVO> result = flightService.find(map);
				System.out.println(result.size());
				
				CountVO countVO = new CountVO();
				countVO = flightService.total();
				System.out.println(countVO.getTotalCount());
				int total = countVO.getTotalCount();
				
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("totalCount", total);
				responseMap.put("pageNo", pageNo);
				responseMap.put("numOfRows", numOfRows);
				
				flightService.total();
				
				if (parsedItemVOs.size() != result.size()) {
						
					for (ItemVO vo : parsedItemVOs) {
//						if (itemList.size() != result.size()) {
//							
//							for (ItemVO vo : itemList) {
						FlightVO flightVO = new FlightVO();
						flightVO.setAirlineNm(vo.getAirlineNm());
						flightVO.setArrAirportNm(vo.getArrAirportNm());
						flightVO.setArrPlandTime(vo.getArrPlandTime());
						flightVO.setDepAirportNm(vo.getDepAirportNm());
						flightVO.setDepPlandTime(vo.getDepPlandTime());
						flightVO.setEconomyCharge(vo.getEconomyCharge());
						flightVO.setPrestigeCharge(vo.getPrestigeCharge());
						flightVO.setVihicleId(vo.getVihicleId());
						
						flightmapper.insertFlight(flightVO);
					}
					responseMap.put("data", flightService.find(map));
					
					
					
					return ResponseEntity.ok(responseMap);
				}
				
				Map<String, Object> apiResponseMap = new HashMap<>();
				apiResponseMap.put("numOfRows", numOfRows);
				apiResponseMap.put("pageNo", pageNo);
				apiResponseMap.put("totalCount", totalCount);
				apiResponseMap.put("data", parsedItemVOs);//객체list<-flight에 대한
				
				return ResponseEntity.ok(apiResponseMap);//content length 차이날때 사용

				//response.getHeaders().setContentLength(response.getBody().toString().length());
				
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
   
}