package com.example.demo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.example.demo.vo.SeatVO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

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
public class FlightController {
	
	@Autowired
	private FlightMapper flightmapper;


	@Autowired
	private FlightService flightService;
	
	Gson gson = new Gson();

	
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
					Type type = new TypeToken<List<ItemVO>>(){}.getType();
					parsedItemVOs.addAll(gson.fromJson(itemJe, type));
				}
				
				
				
				if (!response.getStatusCode().is2xxSuccessful()) {	//status 코드
					throw new Exception();
					
				}
				System.out.println(response);//response{header
				System.out.println(itemJe);//[]
				System.out.println(parsedItemVOs);//dto
//				----파싱하여 타입검사-----------------------------------------------------------------
				System.out.println(parsedItemVOs.get(0));
				ItemVO firstParsedItem = parsedItemVOs.get(0);
				String depAirportNm = firstParsedItem.getDepAirportNm();
				String arrAirportNm = firstParsedItem.getArrAirportNm();
				String plandTime = firstParsedItem.getDepPlandTime();
				String airlineNm = firstParsedItem.getAirlineNm();
//				int charg = firstParsedItem.getEconomyCharge();
//				System.out.println(charg);
				System.out.println(depAirportNm);//출발지
				System.out.println(arrAirportNm);//도착지
				System.out.println(plandTime);
				System.out.println(airlineNm);
				//페이징 처리
				Map<String, Object> map = new HashMap<>();
				map.put("pageNo", (pageNo - 1) * numOfRows);
				map.put("numOfRows", numOfRows);
				map.put("depAirportNm", depAirportNm);
				map.put("arrAirportNm", arrAirportNm);
				map.put("depPlandTime", plandTime);
				//출발지&도착지에 따른 DB검색결과
				List<FlightVO> result = flightService.find(map);
				System.out.println(result);
				
				System.out.println(airlineNm);
				Map<String, Object> airline_map = new HashMap<>();
				airline_map.put("pageNo", (pageNo - 1) * numOfRows);
				airline_map.put("numOfRows", numOfRows);
				airline_map.put("depAirportNm", depAirportNm);
				airline_map.put("arrAirportNm", arrAirportNm);
				airline_map.put("depPlandTime", plandTime);
				airline_map.put("airlineNm", airlineNm);
				//출발지&도착지에 따른 DB검색결과
				List<FlightVO> airline_result = flightService.findAirline(airline_map);
				
				System.out.println("API : " + itemJe);//[]
				System.out.println("DB : " + result);
				System.out.println("DB + airlineNm : " + airline_result);
//--------------1) API(parsedItemVOs), DB(result) 검색값----------------------------------------------------------
				
				Map<String, Object> total_map = new HashMap<>();
				total_map.put("depAirportNm", depAirportNm);
				total_map.put("arrAirportNm", arrAirportNm);
				total_map.put("depPlandTime", plandTime);
				total_map.put("airlineNm", airlineNm);
				
				CountVO countVO = new CountVO();
				//DB flight 데이터 총 개수 
				countVO = flightService.total(total_map);
				System.out.println("[DB] total count : " + countVO.getTotalCount());
				int total = countVO.getTotalCount();
				
				Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("totalCount", total);
				responseMap.put("pageNo", (pageNo-1) * numOfRows);
				responseMap.put("numOfRows", numOfRows);
				
				System.out.println("DB 찾아온 값 수 : " + result.size());
				System.out.println("DB에서 찾아온 (airline없는)데이터 : " + result);
				System.out.println("API 찾아온 값 수 : " + parsedItemVOs.size());
				
				//airlineId가 없는 경우
				if (airlineId == "" || airlineId == null) {
					if (parsedItemVOs.size() == result.size()) {
						responseMap.put("data", result);
						return ResponseEntity.ok(responseMap);
					} else {
						// parsedItemVOs와 result의 크기가 다른 경우
						List<FlightVO> parsedItemList = new ArrayList<>();

						for (ItemVO item : parsedItemVOs) {
						    // item이 airline_result에도 존재하는지 확인
						    boolean isDuplicate = false;
						    for (FlightVO resultItem : result) {
						        if (resultItem.getAirlineNm().equals(item.getAirlineNm())
						            && resultItem.getArrAirportNm().equals(item.getArrAirportNm())
						            && resultItem.getDepAirportNm().equals(item.getDepAirportNm())
						            && resultItem.getArrPlandTime().equals(item.getArrPlandTime())
						            && resultItem.getDepPlandTime().equals(item.getDepPlandTime())) {
						            // 항목이 중복됨
						            isDuplicate = true;
						            break;
						        }
						    }

						    if (!isDuplicate) {
						        // 중복되지 않는 항목일 경우, parsedItemList에 추가
						        FlightVO flightVO = new FlightVO();
						        String uuid = RandomStringUtils.random(36, true, true);
						        flightVO.setFlightId(uuid + "");
						        flightVO.setAirlineNm(item.getAirlineNm());
						        flightVO.setArrAirportNm(item.getArrAirportNm());
						        flightVO.setArrPlandTime(item.getArrPlandTime());
						        flightVO.setDepAirportNm(item.getDepAirportNm());
						        flightVO.setDepPlandTime(item.getDepPlandTime());
						        flightVO.setEconomyCharge(item.getEconomyCharge());
						        flightVO.setPrestigeCharge(item.getPrestigeCharge());
						        flightVO.setVihicleId(item.getVihicleId());
						        parsedItemList.add(flightVO);
						    }
						}

						// parsedItemList에 있는 항목을 DB에 저장
						for (FlightVO flightVO : parsedItemList) {
						    flightmapper.insertFlight(flightVO);
						}

						responseMap.put("data", result);
						return ResponseEntity.ok(responseMap);
					}
				} else {
				//airlineId가 있는 경우
					List<FlightVO> parsedItemList = new ArrayList<>();

					for (ItemVO item : parsedItemVOs) {
					    // item이 airline_result에도 존재하는지 확인
					    boolean isDuplicate = false;
					    for (FlightVO resultItem : airline_result) {
					        if (resultItem.getAirlineNm().equals(item.getAirlineNm())
					            && resultItem.getArrAirportNm().equals(item.getArrAirportNm())
					            && resultItem.getDepAirportNm().equals(item.getDepAirportNm())
					            && resultItem.getArrPlandTime().equals(item.getArrPlandTime())
					            && resultItem.getDepPlandTime().equals(item.getDepPlandTime())) {
					            // 항목이 중복됨
					            isDuplicate = true;
					            break;
					        }
					    }

					    if (!isDuplicate) {
					        // 중복되지 않는 항목일 경우, parsedItemList에 추가
					        FlightVO flightVO = new FlightVO();
					        String uuid = RandomStringUtils.random(36, true, true);
					        flightVO.setFlightId(uuid + "");
					        flightVO.setAirlineNm(item.getAirlineNm());
					        flightVO.setArrAirportNm(item.getArrAirportNm());
					        flightVO.setArrPlandTime(item.getArrPlandTime());
					        flightVO.setDepAirportNm(item.getDepAirportNm());
					        flightVO.setDepPlandTime(item.getDepPlandTime());
					        flightVO.setEconomyCharge(item.getEconomyCharge());
					        flightVO.setPrestigeCharge(item.getPrestigeCharge());
					        flightVO.setVihicleId(item.getVihicleId());
					        parsedItemList.add(flightVO);
					    }
					// parsedItemList에 있는 항목을 DB에 저장
					for (FlightVO flightVO : parsedItemList) {
					    flightmapper.insertFlight(flightVO);
					}

					responseMap.put("data", airline_result);
					return ResponseEntity.ok(responseMap);
				}
			}
//					if (parsedItemVOs.size() == airline_result.size()) {
//						responseMap.put("data", airline_result);
//						return ResponseEntity.ok(responseMap);
//					} else {
//						// parsedItemVOs와 result의 크기가 다른 경우
//
//						// items안에 값이 같은게 있으면 그것을 제외하고 저장
//						FlightVO flightVO = new FlightVO();
//
//						for (ItemVO vo : parsedItemVOs) {
//							String uuid = RandomStringUtils.random(36, true, true);
//							flightVO.setFlightId(uuid + "");
//							flightVO.setAirlineNm(vo.getAirlineNm());
//							flightVO.setArrAirportNm(vo.getArrAirportNm());
//							flightVO.setArrPlandTime(vo.getArrPlandTime());
//							flightVO.setDepAirportNm(vo.getDepAirportNm());
//							flightVO.setDepPlandTime(vo.getDepPlandTime());
//							flightVO.setEconomyCharge(vo.getEconomyCharge());
//							flightVO.setPrestigeCharge(vo.getPrestigeCharge());
//							flightVO.setVihicleId(vo.getVihicleId());
//
//							flightmapper.insertFlight(flightVO);
//						}
//					}
//				
//				
//				
//					
//				Map<String, Object> apiResponseMap = new HashMap<>();
//				apiResponseMap.put("numOfRows", numOfRows);
//				apiResponseMap.put("pageNo", pageNo);
//				apiResponseMap.put("totalCount", totalCount);
//				apiResponseMap.put("data", parsedItemVOs);//객체list<-flight에 대한
//				
//				return ResponseEntity.ok(apiResponseMap);//content length 차이날때 사용
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		
		@PostMapping(value = "/flight")
		public ResponseEntity<?> flightInsert(@RequestBody List<FlightVO> vo) throws Exception {
			int test = flightService.insertFlight(vo);
			System.out.println("flight테스트" + test);
			return ResponseEntity.ok().body(test);
		}
		

   
}