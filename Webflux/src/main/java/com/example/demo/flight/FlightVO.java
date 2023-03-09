package com.example.demo.flight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlightVO {
	private Integer id;
	private String vihicleId;
	private String airlineNm;
	private String depPlandTime;
	private String arrPlandTime;
	private Integer economyCharge;
	private Integer prestigeCharge;
	private String depAirportNm;
	private String arrAirportNm;
	private String flag;
	private Integer totalcount;
}