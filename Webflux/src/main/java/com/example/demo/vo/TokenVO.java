package com.example.demo.vo;



import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenVO {
	private String id;
	private String refreshToken;
	private String accessToken;
	private Date createDate;
	private Date updateDate;
}
