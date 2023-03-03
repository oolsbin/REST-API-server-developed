package com.example.demo.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestDto {

    private String id;
    private String pw;
//	private String refreshtoken;
}