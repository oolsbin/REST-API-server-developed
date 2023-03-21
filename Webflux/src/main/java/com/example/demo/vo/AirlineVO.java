package com.example.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "항공사 모델")
public class AirlineVO {
	@ApiModelProperty(value = "항공사 아이디", required = true)
	private String airlineId;
	@ApiModelProperty(value = "항공사 이름", required = true)
	private String airlineNm;
//	@ApiModelProperty(value = "고유번호", required = true)
//	private String pk_id;
}
