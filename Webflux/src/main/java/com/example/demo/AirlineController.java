package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.airline.AirlineService;
import com.example.demo.airline.AirlineServiceImpl;
import com.example.demo.airline.AirlineVO;


@RestController
public class AirlineController {

	@Autowired
	private AirlineService airlineService;
	
	//db에 저장
	@PostMapping("/insert-airline")
	public ResponseEntity<?> insertAirline(@RequestBody AirlineVO vo) throws Exception {
		if(airlineService.insertAirline(vo)!=1) {
		StringBuffer msg = new StringBuffer();
		msg.append("저장안됨");
		return ResponseEntity.ok().body(msg);
		};
		
		StringBuffer msg = new StringBuffer();
		msg.append("저장됨");
		return ResponseEntity.ok().body(msg);
	}
	
	//db
	@RequestMapping(value="/select-airline")
	public ResponseEntity<?> selectAirline(@RequestBody AirlineVO vo) throws Exception{
//		if(airlineService.selectAirline(vo) != null) {
		AirlineVO test = airlineService.selectAirline(vo);
		System.out.println("조회테스트" + test);
		return ResponseEntity.ok().body(test);
//		}
	}

	
//	@RequestMapping(value="/modify")
//	public String modify(String airlineId){
//		airlineService.updateAirline(airlineId);
//		return "modify";
//	}
//	
//	@RequestMapping("/delete")
//	public String modify(String airlineId){
//		model.addAttribute("vo", airlineService.selectAirline(airlineId));
//		return "delete";
//	}
}
