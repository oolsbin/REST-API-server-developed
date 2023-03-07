package com.example.demo;

import java.io.IOException;
import java.net.URI;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.flightdto.ListVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class FlightController {

	
	//body..page가져와야한다..
	@GetMapping(value = "/flight")
	@Operation(summary = "항공운항정보 목록 조회", description = "출/도착지를 기준으로 국내선 항공운항정보 목록을 조회하는 기능")
	public ResponseEntity<?> flightSelect
		   (@RequestParam(value = "depAirportId", required = false) String depAirportId,
		    @RequestParam(value = "arrAirportId", required = false) String arrAirportId,
		    @RequestParam(value = "depPlandTime", required = false) String depPlandTime,
			@RequestParam(value = "airlineId", required = false) String airlineId,
			@RequestParam(value = "numOfRows", required = false) Integer numOfRows,
			@RequestParam(value = "pageNo", required = false) Integer pageNo		
			)throws IOException, ParseException  {

		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json");
			HttpEntity<?> entity = new HttpEntity<>(headers);
			
			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getFlightOpratInfoList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&depAirportId=" + depAirportId + "&arrAirportId=" + arrAirportId + "&depPlandTime=" + depPlandTime + "&airlineId=" + airlineId 
					+ "&numOfRows=" + numOfRows + "&pageNo=" + pageNo + "&_type=json";

			URI uri = new URI(urlBuilder);


			final ResponseEntity<?> response = restTemplate
					.exchange(uri
							, HttpMethod.GET
							, entity
							, ListVO.class);

			
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
	
	

}
