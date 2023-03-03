//package com.example.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.airport.AirportService;
//
//@RestController
//public class AirportDbController {
//	
//	@Autowired
//	private AirportService airportService;
//	
//	// DB연결 Test클래스
//	@RequestMapping(value="/index")
//	public String index() throws Exception{
//		String test = airportService.selectTest();
//		System.out.println("조회테스트" + test);
//		return "index";
//	}
//}
