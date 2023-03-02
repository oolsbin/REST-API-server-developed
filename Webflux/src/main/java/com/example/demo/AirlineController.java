package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.airline.AirlineService;
import com.example.demo.airline.AirlineServiceImpl;

@RestController
public class AirlineController {
	
	
	@Autowired
	private AirlineServiceImpl airlineServiceImpl;

	@Autowired
	private AirlineService airlineService;
	
	// DB연결 Test클래스
	@RequestMapping(value="/index")
	public String selectAirline(){
		String test = airlineService.selectAirline();
		System.out.println("조회테스트" + test);
		return "selectAirline";
	}
	
//	@RequestMapping("/detail")
//	public String detail(String airlineId, Model model) {
//		AirlineVO vo = airlineService.code_detail(airlineId);
//		model.addAttribute("vo", vo);
//		return "detail";
//
//	}
//	
//	@RequestMapping(value="/modify")
//	public String modify(String airlineId, Model model){
//		model.addAttribute("vo", airlineService.selectAirline(airlineId));
//		return "modify";
//	}
}
