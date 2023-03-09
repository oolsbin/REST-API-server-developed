package com.example.demo;

import java.io.IOException;
import java.net.URI;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.airportdto.ListVO;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
public class AirportController {
	
	@GetMapping(value = "/airport")
	@Operation(summary = "공항 목록 조회", description = "국내 공항 목록을 조회하는 기능")
	public ResponseEntity<?> airportSelect
		   (@RequestParam(value = "airportId", required = false) String airportId,
			@RequestParam(value = "airportNm", required = false) String airportNm)
			throws IOException, ParseException {

		try {
			RestTemplate restTemplate = new RestTemplate();//RestTemplate
			HttpHeaders headers = new HttpHeaders();//헤더검색
			headers.add("Content-Type", "application/json");
			HttpEntity<?> entity = new HttpEntity<>(headers);

			String urlBuilder = "http://apis.data.go.kr/1613000/DmstcFlightNvgInfoService/getArprtList?"
					+ "serviceKey=s%2FJMx%2B0d4t%2Ffp3JEpST7EJe7bhAJ7Tvuh%2FXkexlOqbuUEzEZxeUBH2UZ%2BXHjwDN8%2Fywz%2F9a%2BFGIUE6k%2FqcmZTg%3D%3D"
					+ "&airportId=" + airportId + "&airportNm=" + airportNm + "&_type=json";
			

			URI uri = new URI(urlBuilder);
			
			final ResponseEntity<?> response = restTemplate
					.exchange(uri
							, HttpMethod.GET
							, entity
							, ListVO.class);

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

}
