package com.example.demo.refresh;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenVO {
	private String refreshToken;
	private String userAgent;
	private String accessToken;
//	private String id;
}
