package com.example.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "공항 모델")
public class AirportVO {
	@ApiModelProperty(value = "공항 아이디", required = true)
	private String airportId;
	@ApiModelProperty(value = "공항 이름", required = true)
	private String airportNm;
}
