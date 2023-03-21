package com.example.demo.controller;

import java.io.IOException;
import java.net.URI;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.service.AirportService;
import com.example.demo.vo.AirportVO;
import com.example.demo.vo.airportvo.ListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "공항정보")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RestController
public class AirportController {

	@Autowired
	private AirportService airportService;

	@GetMapping(value = "/airport")
	@ApiOperation(value = "API 공항 목록 조회", notes = "공공데이터 API에 등록된 국내 공항의 목록을 조회하는 기능")
	public ResponseEntity<?> airportSelect(
			@ApiParam(value = "공항아이디", required = true, example = "NAARKSS") @RequestParam(value = "airportId", required = false) String airportId,
			@ApiParam(value = "공항이름", required = true, example = "김포") @RequestParam(value = "airportNm", required = false) String airportNm)
			throws IOException, ParseException {

		try {
			RestTemplate restTemplate = new RestTemplate();// RestTemplate
			HttpHeaders headers = new HttpHeaders();// 헤더검색
			headers.add("Content-Type", "application/json");
			HttpEntity<?> entity = new HttpEntity<>(headers);

			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getArprtList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&airportId=" + airportId + "&airportNm=" + airportNm + "&_type=json";

			URI uri = new URI(urlBuilder);

			final ResponseEntity<?> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ListVO.class);

			if (response.getStatusCodeValue() == 200) {
				return ResponseEntity.ok(response);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// db에 저장
	@ApiOperation(value = "공항 목록 생성", notes = "국내 공항 목록을 생성하는 기능")
	@PostMapping("/airport")
	public ResponseEntity<?> insertairport(@RequestBody AirportVO vo) throws Exception {
		if (airportService.insertAirport(vo) != 1) {
			HttpStatus status = HttpStatus.BAD_REQUEST;
			String message = "저장실패";
			return new ResponseEntity<>(message, status);
		}

		HttpStatus status = HttpStatus.OK;
		String message = "저장되었습니다.";
		return new ResponseEntity<>(message, status);
	}

	// db
//		@ApiOperation(value = "공항 목록 조회", notes = "국내 공항 목록을 조회하는 기능")
//		@GetMapping(value="/airport-read")
//		public ResponseEntity<?> selectAirport(@RequestBody AirportVO vo) throws Exception{
////			if(airportService.selectairport(vo) != null) {
//			AirportVO test = airportService.seletAirport(vo);
//			System.out.println("조회테스트" + test);
//			return ResponseEntity.ok().body(test);
////			}
//		}

	@ApiOperation(value = "공항 목록 수정", notes = "국내 공항 목록을 수정하는 기능")
	@PutMapping(value = "/airport")
	public ResponseEntity<?> updateAirport(@RequestBody AirportVO vo) throws Exception {
		airportService.updateAirport(vo);
		HttpStatus status = HttpStatus.OK;
		String message = "수정되었습니다.";
		return new ResponseEntity<>(message, status);
	}

	@ApiOperation(value = "공항 목록 삭제", notes = "국내 공항 목록을 삭제하는 기능")
	@DeleteMapping(value = "/airport")
	public ResponseEntity<?> deleteAirport(@RequestBody AirportVO vo) throws Exception {
		airportService.deleteAirport(vo);
		HttpStatus status = HttpStatus.OK;
		String message = "삭제되었습니다.";
		return new ResponseEntity<>(message, status);
	}

}
