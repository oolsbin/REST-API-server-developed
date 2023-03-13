package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	private String id;
	private String pw;
	
	private String name;
	private String address;
	private String email;
	private String phoneNumber;
	
//	private String refreshToken;
	
    // 생성자
    public UserVO() {

    }

//    // to String
//    @Override
//    public String toString() {
//        return "MemberVO [id=" + id + ", password=" + password + "]";
//    }
}
