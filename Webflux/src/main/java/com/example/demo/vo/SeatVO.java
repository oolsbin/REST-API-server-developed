package com.example.demo.vo;

import java.sql.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "SeatVO : 항공편예약 모델")
public class SeatVO {
	@ApiModelProperty(value = "비행기 아이디", required = true)
	private String flightId;//비행기아이디
	@ApiModelProperty(value = "좌석등급", required = true)
	private String seatType;//좌석
	@ApiModelProperty(value = "예약인원수", required = true)
	private String personal;//인원수
	@ApiModelProperty(value = "예약번호", required = true)
	private String reservationId;//예약여부
	@ApiModelProperty(value = "사용자ID", required = true)
	private String userId;//사용자Id
	@ApiModelProperty(value = "예약 전체금액", required = true)
	private int chargeSum;
	@ApiModelProperty(value = "예약일자", required = true)
	private Date createDate;
}
