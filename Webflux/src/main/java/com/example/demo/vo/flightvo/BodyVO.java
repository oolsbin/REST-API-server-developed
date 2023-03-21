package com.example.demo.vo.flightvo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class BodyVO {
	private ItemsVO items;
	
	private Integer numOfRows;
	private Integer pageNo;
	private Integer totalCount;
}

