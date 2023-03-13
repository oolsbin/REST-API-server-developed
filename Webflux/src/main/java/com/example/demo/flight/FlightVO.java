package com.example.demo.flight;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FlightVO {
	private String flight_id;//아이디
	private String vihicleId;//항공편명
	private String airlineNm;//항공사명
	private String depPlandTime;//출발시간
	private String arrPlandTime;//도착시간
	private Integer economyCharge;//일반석운임
	private Integer prestigeCharge;//비즈니스석운임
	private String depAirportNm;//출발공항
	private String arrAirportNm;//도착공항
	private String flag;//프론트에서 들어오는 "add, update, delete" 값
}