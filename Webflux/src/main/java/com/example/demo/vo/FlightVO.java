package com.example.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "FlightVO : 항공편 모델")
public class FlightVO {
	@ApiModelProperty(value = "항공편 아이디", required = true)
	private String flightId;//아이디
	@ApiModelProperty(value = "항공편명", required = true)
	private String vihicleId;//항공편명
	@ApiModelProperty(value = "항공사명", required = true)
	private String airlineNm;//항공사명
	@ApiModelProperty(value = "출발시간", required = true)
	private String depPlandTime;//출발시간
	@ApiModelProperty(value = "도착시간", required = true)
	private String arrPlandTime;//도착시간
	@ApiModelProperty(value = "일반석운임", required = true)
	private Integer economyCharge;//일반석운임
	@ApiModelProperty(value = "비즈니스석운임", required = true)
	private Integer prestigeCharge;//비즈니스석운임
	@ApiModelProperty(value = "출발공항", required = true)
	private String depAirportNm;//출발공항
	@ApiModelProperty(value = "도착공항", required = true)
	private String arrAirportNm;//도착공항
	@ApiModelProperty(value = "http메소드 값", required = true)
	private String flag;//프론트에서 들어오는 "add, update, delete" 값
}