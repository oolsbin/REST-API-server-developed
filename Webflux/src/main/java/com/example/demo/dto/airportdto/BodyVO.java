package com.example.demo.dto.airportdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class BodyVO {
	private ItemsVO items;
	
	private Integer numOfRows;
	private Integer pageNo;
}
