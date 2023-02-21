package com.example.demo.filghtdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class BodyVO {
	private ItemsVO items;
	
	private Integer numOfRows;
	private Integer pageNo;
}
