package com.example.demo.dto.flightdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVO {
//	private String depAirportId;
//	private String arrAirportId;
//	private Integer depPlandTime;
//	private String airlineId;
	
//	private Integer numOfRows;
//	private Integer pageNo;
//	private Integer totalCount;

	private String vihicleId;
	private String airlineNm;
	private String depPlandTime;
	private String arrPlandTime;
	private Integer economyCharge;
	private Integer prestigeCharge;
	private String depAirportNm;
	private String arrAirportNm;
}
