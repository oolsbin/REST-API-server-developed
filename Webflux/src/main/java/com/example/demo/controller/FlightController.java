package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.mapper.FlightMapper;
import com.example.demo.service.FlightService;
import com.example.demo.token.JwtUtil;
import com.example.demo.vo.FlightVO;
import com.example.demo.vo.flightvo.ItemVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "항공운항정보")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
//@RequiredArgsConstructor
@Slf4j
public class FlightController {
	
	@Autowired
	private FlightMapper flightmapper;


	@Autowired
	private FlightService flightService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	Gson gson = new Gson();
	private Gson pretty_gson = new GsonBuilder().setPrettyPrinting().create();

	
		//total count
		@GetMapping(value = "/flight", produces="application/json;charset=UTF-8")
		@ApiOperation(value = "API 항공운항정보 목록 조회", notes = "출/도착지를 기준으로 국내선 항공운항정보 목록을 조회하는 기능")
//		@Scheduled(fixedDelay = 300000)
		public ResponseEntity<?> flightSelect
			   (@ApiParam(value = "출발공항ID", required = true, example = "NAARKJJ")
   			    @RequestParam(value = "depAirportId", required = false) String depAirportId,
   		  	    @ApiParam(value = "도착공항ID", required = true, example = "NAARKPC")
			    @RequestParam(value = "arrAirportId", required = false) String arrAirportId,
			    @ApiParam(value = "출발일", required = true, example = "20201201")
			    @RequestParam(value = "depPlandTime", required = false) String depPlandTime,
			    @ApiParam(value = "항공사ID", required = true, example = "AAR")
				@RequestParam(value = "airlineId", required = false) String airlineId,
				@ApiParam(value = "한 페이지 결과 수", required = true, example = "10")
				@RequestParam(value = "numOfRows", required = false) Integer numOfRows,//시리얼라이져가 되서 보내주기 때문에 ㄱㅊ
				@ApiParam(value = "페이지 번호", required = true, example = "1")
				@RequestParam(value = "pageNo", required = false) Integer pageNo			
				)throws Exception, IOException, ParseException {

			
//				String airlineNm = flightService.airlineNm(airlineId);
//				String depAirportNm = flightService.airportNm(depAirportId);
//				String arrAirportNm = flightService.airportNm(arrAirportId);
//				System.out.println(flightService.airlineNm(airlineId));
//				System.out.println(flightService.airportNm(depAirportId));
//				
//				Map<String, Object> map = new HashMap<>();
//				map.put("pageNo", (pageNo-1) * numOfRows);
//				map.put("numOfRows", numOfRows);
//				map.put("airlineNm", airlineNm);
//				map.put("depAirportNm", depAirportNm);
//				map.put("arrAirportNm", arrAirportNm);
//				
//				//출발지&도착지에 따른 DB검색결과
//				List<FlightVO> result = flightService.find(map);
//				System.out.println(result.size());
				
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
				log.info(response+"");
//				System.out.println(response);
				}catch(RestClientException e){
					e.printStackTrace();
					HttpStatus status = HttpStatus.NOT_FOUND;
					String message = "데이터가 없습니다.";
					
					return new ResponseEntity<>(message, status);
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
				log.info("totalCount = " + totalCount+"");
//				System.out.println("totalCount = " + totalCount);
				List<ItemVO> parsedItemVOs = new ArrayList<>();
				
				if (itemJe.isJsonPrimitive()) {
					HttpStatus status = HttpStatus.OK;
					Map<String, Object> map = new HashMap<>();
					map.put("status", HttpStatus.NOT_FOUND);
					map.put("msg", "데이터가 없습니다.");
					return new ResponseEntity<>(map, status);
//					HttpStatus status = HttpStatus.NOT_FOUND;
//					String message = "데이터가 없습니다.";
//					
//					return new ResponseEntity<>(message, status);
				} else if (itemJe.isJsonObject()) {
					ItemVO stringItem = gson.fromJson(itemJe, ItemVO.class);
					parsedItemVOs.add(stringItem);
				} else if (itemJe.isJsonArray()) {
					Type type = new TypeToken<List<ItemVO>>(){}.getType();
					parsedItemVOs.addAll(gson.fromJson(itemJe, type));
				}
				
				
				
				if (!response.getStatusCode().is2xxSuccessful()) {	//status 코드
					throw new Exception();
					
				}
//				System.out.println(response);//response{header
//				System.out.println(itemJe);//[]
//				System.out.println(parsedItemVOs);//dto
//				----파싱하여 타입검사-----------------------------------------------------------------
//				System.out.println(parsedItemVOs.get(0));
				ItemVO firstParsedItem = parsedItemVOs.get(0);
				String depAirportNm = firstParsedItem.getDepAirportNm();
				String arrAirportNm = firstParsedItem.getArrAirportNm();
				String plandTime = firstParsedItem.getDepPlandTime();
				String airlineNm = firstParsedItem.getAirlineNm();
				

//				System.out.println(depAirportNm);//출발지
//				System.out.println(arrAirportNm);//도착지
//				System.out.println(plandTime);
//				System.out.println(airlineNm);
				//페이징 처리
				Map<String, Object> map = new HashMap<>();
				map.put("pageNo", (pageNo - 1) * numOfRows);
				map.put("numOfRows", numOfRows);
				map.put("depAirportNm", depAirportNm);
				map.put("arrAirportNm", arrAirportNm);
				map.put("depPlandTime", plandTime.substring(0, 8));
				//출발지&도착지에 따른 DB검색결과
				List<FlightVO> result = flightService.find(map);
//				System.out.println(result);
//				
//				System.out.println(airlineNm);
				Map<String, Object> airline_map = new HashMap<>();
				airline_map.put("pageNo", (pageNo - 1) * numOfRows);
				airline_map.put("numOfRows", numOfRows);
				airline_map.put("depAirportNm", depAirportNm);
				airline_map.put("arrAirportNm", arrAirportNm);
				airline_map.put("depPlandTime", plandTime.substring(0, 8));
				airline_map.put("airlineNm", airlineNm);
				//출발지&도착지에 따른 DB검색결과
				List<FlightVO> airline_result = flightService.findAirline(airline_map);
				
//				System.out.println("API : " + itemJe);//[]
//				System.out.println("DB : " + result);
//				System.out.println("DB + airlineNm : " + airline_result);
//--------------1) API(parsedItemVOs), DB(result) 검색값----------------------------------------------------------
				
				Map<String, Object> total_map = new HashMap<>();
				total_map.put("depAirportNm", depAirportNm);
				total_map.put("arrAirportNm", arrAirportNm);
				total_map.put("depPlandTime", plandTime.substring(0, 8));
				
//				CountVO countVO = new CountVO();
//				//DB flight 데이터 총 개수 
//				countVO = flightService.total(total_map);
//				int total = countVO.getTotalCount();
//				System.out.println("[DB] total count : " + totalCount);
				
				Map<String, Object> total_airline_map = new HashMap<>();
				total_airline_map.put("depAirportNm", depAirportNm);
				total_airline_map.put("arrAirportNm", arrAirportNm);
				total_airline_map.put("depPlandTime", plandTime.substring(0, 8));
				total_airline_map.put("airlineNm", airlineNm);

//				System.out.println("[DB](airline) total count : " + totalCount);
				
//--------------2) 검색값에 따른 페이징 total 값 -----------------------------------------------------------------------
				
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("totalCount", totalCount);//////////////////
				responseMap.put("pageNo", pageNo);
				responseMap.put("numOfRows", numOfRows);
				
				Map<String, Object> responseMap_airline = new HashMap<>();
				responseMap_airline.put("totalCount", totalCount);//////////////////
				responseMap_airline.put("pageNo", pageNo);
				responseMap_airline.put("numOfRows", numOfRows);
				
//				System.out.println("DB 찾아온 값 수 : " + result.size());
//				System.out.println("DB에서 찾아온 (airline없는)데이터 : " + result);
//				System.out.println("DB + airlineNm : " + airline_result);
//				System.out.println("API 찾아온 값 수 : " + parsedItemVOs.size());
				
				//1-1) airlineId가 없는 경우
				if (airlineId == "" || airlineId == null) {
					if (parsedItemVOs.size() == result.size()) {
						responseMap.put("data", result);
						responseMap.put("status", Status.OK);
						responseMap.put("msg", "검색한 항공편이 조회 되었습니다.");
						log.info("================================= flight response:\n" + pretty_gson.toJson(responseMap));
						return ResponseEntity.ok(responseMap);
					} else {
						
						FlightVO flightVO = new FlightVO();
						
						for (ItemVO vo : parsedItemVOs) {
							String uuid = RandomStringUtils.random(36, true, true);
							flightVO.setFlightId(uuid+"");
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

						Map<String, Object> resultMap = new HashMap<>();
						resultMap.put("pageNo", pageNo);
						resultMap.put("numOfRows", numOfRows);
						resultMap.put("depAirportNm", depAirportNm);
						resultMap.put("arrAirportNm", arrAirportNm);
						resultMap.put("depPlandTime", plandTime.substring(0, 8));
						resultMap.put("status", Status.OK);
						//출발지&도착지에 따른 DB검색결과
						System.out.println(result);
						responseMap.put("data", flightService.find(resultMap));
						responseMap.put("status", Status.OK);
						responseMap.put("status", Status.OK);
						responseMap.put("msg", "검색한 항공편이 조회 되었습니다.");
//						responseMap.put("api", parsedItemVOs);
						log.info("================================= flight response:\n" + pretty_gson.toJson(responseMap));
						return ResponseEntity.ok(responseMap);
					}
					
				} else {
				//1-2)airlineId가 있는 경우
					if (parsedItemVOs.size() == airline_result.size()) {
						responseMap_airline.put("data", airline_result);
						responseMap_airline.put("status", Status.OK);
						responseMap_airline.put("msg", "검색한 항공편이 조회 되었습니다.");
						log.info("================================= flight response:\n" + pretty_gson.toJson(responseMap_airline));
						return ResponseEntity.ok(responseMap_airline);
					}
					
					FlightVO flightVO = new FlightVO();
					
					for (ItemVO vo : parsedItemVOs) {
						String uuid = RandomStringUtils.random(36, true, true);
						flightVO.setFlightId(uuid+"");
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
					
					Map<String, Object> resultMap = new HashMap<>();
					resultMap.put("pageNo", pageNo);
					resultMap.put("numOfRows", numOfRows);
					resultMap.put("depAirportNm", depAirportNm);
					resultMap.put("arrAirportNm", arrAirportNm);
					resultMap.put("airlineNm", airlineNm);
					resultMap.put("depPlandTime", plandTime.substring(0, 8));
					//출발지&도착지에 따른 DB검색결과
					System.out.println(result);
					responseMap.put("data", flightService.findAirline(resultMap));
					responseMap.put("status", Status.OK);
					responseMap.put("msg", "검색한 항공편이 조회 되었습니다.");
					log.info("================================= flight response:\n" + pretty_gson.toJson(responseMap));
//					responseMap_airline.put("api", parsedItemVOs);
					return ResponseEntity.ok(responseMap);
//					responseMap.put("status", Status.OK);
//					responseMap.put("msg", "항공사에 따른 항공편 전체 조회");
//					return ResponseEntity.ok(responseMap);
				
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@ApiOperation(value = "항공운항정보 수정", notes = "flag를 통해 항공운항정보를 수정하는 기능")
		@PutMapping(value = "/flight", produces="application/json;charset=UTF-8")
		public ResponseEntity<?> flightUpdate(
				@RequestBody List<FlightVO> vo,
				@RequestHeader HttpHeaders headers) throws Exception {
			
		
			String authToken = headers.getFirst("Authorization");
			if (authToken != null & authToken.startsWith("Bearer ")) {
				String accessToken = null;
				accessToken = authToken.substring(7);
				
				Jws<Claims> claims = jwtUtil.getClaims(accessToken);//코드 복호화+서명검증
		    	boolean isTokenValid = jwtUtil.validateToken(claims);//토큰 만료시간 검증
		    	String id = jwtUtil.getKey(claims);//payload의 id를 취득
		    	
		    	log.info("token 복호화 : " + claims);
		    	log.info("토큰만료시간 : " + isTokenValid);
		    	log.info("payload id 취득 : " + id);
		    	
			int test = flightService.insertFlight(vo);
			System.out.println("flight테스트" + test);

			
			
			List<FlightVO> result = new ArrayList<>();
//			List<FlightVO> result_airline = new ArrayList<>();

			for (FlightVO flight : vo) {
			    Map<String, Object> map = new HashMap<>();
			    map.put("depAirportNm", flight.getDepAirportNm());
			    map.put("arrAirportNm", flight.getArrAirportNm());

//			    if (flight.getAirlineNm() != null) {
//			        map.put("airlineNm", flight.getAirlineNm());
//			        List<FlightVO> searchResult = flightService.responseAirline(map);
//			        result_airline.addAll(searchResult);
//			        return ResponseEntity.ok().body(result_airline);
//			    } else {
			        List<FlightVO> searchResult = flightService.response(map);
			        result.addAll(searchResult);
			        
			        HttpStatus status = HttpStatus.OK;
					String message = "항공편이 수정되었습니다.";
					Map<String, Object> result_map = new HashMap<>();
					result_map.put("status", status);
					result_map.put("msg", message);
					result_map.put("data", result);
					result_map.put("pageNo", 1);
					result_map.put("numOfRows", 10);
					
					log.info("================================= flight-update response:\n" + pretty_gson.toJson(result_map));
			        
			        return ResponseEntity.ok().body(result_map);
//			    }
			}

			
//			pretty_gson.toJson(vo);
//			int result = 0;
//			for(FlightVO vo : voList) {
//				if("add".equals(vo.getFlag())) {
//					result += flightmapper.insertFlight(vo);
//				}else if("update".equals(vo.getFlag())) {
//					result += flightmapper.updateFlight(vo);
//				}else if("delete".equals(vo.getFlag())) {
//					result += flightmapper.deleteFlight(vo);
//				}
//			}
//			return ResponseEntity.ok().body(result_airline);
			}
			return null;
			
		}
		
		
		@ApiOperation(value = "항공운항정보 생성", notes = "flag를 통해 항공운항정보를 생성하는 기능")
		@PostMapping(value = "/flight", produces="application/json;charset=UTF-8")
		public ResponseEntity<?> flightUpdate(
				@RequestBody List<FlightVO> vo
//				,@RequestHeader HttpHeaders headers
				) throws Exception {

			
			
			
		
//			String authToken = headers.getFirst("Authorization");
//			if (authToken != null & authToken.startsWith("Bearer ")) {
//				String accessToken = null;
//				accessToken = authToken.substring(7);
//				
//				Jws<Claims> claims = jwtUtil.getClaims(accessToken);//코드 복호화+서명검증
//		    	boolean isTokenValid = jwtUtil.validateToken(claims);//토큰 만료시간 검증
//		    	String id = jwtUtil.getKey(claims);//payload의 id를 취득
//		    	
//		    	log.info("token 복호화 : " + claims);
//		    	log.info("토큰만료시간 : " + isTokenValid);
//		    	log.info("payload id 취득 : " + id);

		   
		    	
			int test = flightService.insertFlight(vo);
			System.out.println("flight테스트" + test);
//			pretty_gson.toJson(vo);
//			int result = 0;
//			for(FlightVO vo : voList) {
//				if("add".equals(vo.getFlag())) {
//					result += flightmapper.insertFlight(vo);
//				}else if("update".equals(vo.getFlag())) {
//					result += flightmapper.updateFlight(vo);
//				}else if("delete".equals(vo.getFlag())) {
//					result += flightmapper.deleteFlight(vo);
//				}
//			}
			
//			List<FlightVO> data = flightService.insertData(vo);
			
			HttpStatus status = HttpStatus.OK;
			String message = "항공편이 생성되었습니다.";
//			List<FlightVO> result = createFlightData(vo);
			Map<String, Object> result_map = new HashMap<>();
			result_map.put("status", status);
			result_map.put("msg", message);
			result_map.put("data", vo); // 생성된 데이터를 Map에 추가
//			result_map.put("data", result);
			
			log.info("================================= flight-insert response:\n" + pretty_gson.toJson(result_map));
	        
	        return ResponseEntity.ok().body(result_map);
//			return ResponseEntity.ok().body(test);
//			}
//			return null;
			
		}
		
//		private List<FlightVO> createFlightData(List<FlightVO> vo) {
//		    List<FlightVO> flights = new ArrayList<>();
//
//		    for (FlightVO flightVO : vo) {
//		        // FlightVO 정보를 이용해 새로운 Flight 객체 생성
//		    	FlightVO flight = new FlightVO();
//		        flight.setFlightId(flightVO.getFlightId());
//		        flight.setVihicleId(flightVO.getVihicleId());
//		        flight.setAirlineNm(flightVO.getAirlineNm());
//		        flight.setDepPlandTime(flightVO.getDepPlandTime());
//		        flight.setArrPlandTime(flightVO.getArrPlandTime());
//		        flight.setFlightId(flightVO.getFlightId());
//		        flight.setFlightId(flightVO.getFlightId());
//		        // ... 추가적인 정보 설정
//
//		        flights.add(flight);
//		    }
//
//		    return flights;
//		}
		
		@ApiOperation(value = "항공운항정보 삭제", notes = "flag를 통해 항공운항정보를 삭제하는 기능")
		@DeleteMapping(value = "/flight", produces="application/json;charset=UTF-8")
		public ResponseEntity<?> flightDelete(
				@RequestBody List<FlightVO> vo
				) throws Exception {
			
//			// 삭제된 항공 운항 정보를 저장할 리스트 선언
//		    List<FlightVO> deletedFlights = new ArrayList<>();
//			
//		    for (FlightVO flightVO : vo) {
//		        // FlightVO 정보를 이용해 삭제 대상 항공 운항 정보를 조회
//		        FlightVO flight = flightService.getFlightByFlightNumber(flightVO.getFlightNumber());
//		        if (flight == null) {
//		            // 삭제 대상 항공 운항 정보가 없으면 다음으로 넘어감
//		            continue;
//		        }

			int test = flightService.insertFlight(vo);
			
//	        // 삭제된 항공 운항 정보 리스트에 추가
//	        deletedFlights.add(flight);
			
			System.out.println("flight테스트" + test);
			HttpStatus status = HttpStatus.OK;
			String message = "선택한 항공편이 삭제되었습니다.";
			Map<String, Object> result_map = new HashMap<>();
			result_map.put("status", status);
			result_map.put("msg", message);
//			result_map.put("data", result);
//			result_map.put("pageNo", 1);
//			result_map.put("numOfRows", 10);
			
			log.info("================================= flight-update response:\n" + pretty_gson.toJson(result_map));
	        
	        return ResponseEntity.ok().body(result_map);

		}

   
}