package com.example.demo.vo.flightvo;

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
	private Integer economyCharge;//Integer
	private Integer prestigeCharge;//Integer
	private String depAirportNm;
	private String arrAirportNm;
}
