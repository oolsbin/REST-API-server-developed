package com.example.demo.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatVO {
	private String flightId;//비행기아이디
	private String seatType;//좌석
	private String personal;//인원수
	private String reservationId;//예약여부
	private String userId;//사용자Id
	private int chargeSum;
	private Date createDate;
}
