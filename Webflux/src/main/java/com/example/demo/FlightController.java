package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.flight.FlightService;
import com.example.demo.flight.FlightVO;

import io.swagger.v3.oas.annotations.Operation;

@RestController
public class FlightController {

	@Autowired
	private FlightService flightService;
	
	//post로 받을 예정
	@PostMapping(value = "/flight")
	public ResponseEntity<?> flightInsert(@RequestBody List<FlightVO> vo) throws Exception {
		int test = flightService.insertFlight(vo);
		System.out.println("flight 조회테스트" + test);
		return ResponseEntity.ok().body(test);
	}

//	//post로 받을 예정
//	@PutMapping(value = "/flight")
//	public ResponseEntity<?> flightSelect(@RequestBody FlightVO vo) throws Exception {
//		int test = flightService.updateFlight(vo);
//		System.out.println("flight 조회테스트" + test);
//		return ResponseEntity.ok().body(test);
//	}
//	
//	//삭제
//		@DeleteMapping(value = "/flight")
//		public ResponseEntity<?> flightDelete(@RequestBody FlightVO vo) throws Exception {
//			int test = flightService.deleteFlight(vo);
//			System.out.println("flight 조회테스트" + test);
//			return ResponseEntity.ok().body(test);
//		}
	
	

}
