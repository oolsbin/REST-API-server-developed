package com.example.demo.dto.airportdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVO {
	private String depAirportId;
	private String arrAirportId;
	private Integer depPlandTime;
	private String airlineId;
}
