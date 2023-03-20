package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilghtVO {
	
	private String depAirportId; //출발공항ID
	private String arrAirportId; //도착공항ID
	private String depPlandTime; //출발일(YYYYMMDD)
	private String airlineId;	//항공사ID

}
