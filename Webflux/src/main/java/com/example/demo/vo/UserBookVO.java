package com.example.demo.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBookVO {
	private String flightId;//flight id
	private String seatType;//좌석등급
    private	int reservation;//예약여부 0 = 예약안됨, 1 = 예약됨
    private String userId;//사용자 아이디
    private String vihicleId;//항공편명
	private String airlineNm;//항공사명
	private String depPlandTime;//출발시간
	private String arrPlandTime;//도착시간
	private Integer economyCharge;//일반석운임
	private Integer prestigeCharge;//비즈니스석운임
	private String depAirportNm;//출발공항
	private String arrAirportNm;//도착공항
	private int chargeSum;
	private String personal;//인원수
	private Date createDate;
//	private String name;//사용자 이름
//	private String address;//주소
//	private String email;//이메일
//	private String phone_number;//전화번호
	
}
