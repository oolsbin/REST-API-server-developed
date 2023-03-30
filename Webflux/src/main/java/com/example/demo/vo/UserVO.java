package com.example.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "UserVO : 사용자 모델")
public class UserVO {
	@ApiModelProperty(value = "사용자 아이디", required = true)
	private String id;
	@ApiModelProperty(value = "사용자 패스워드", required = true)
	private String pw;
	@ApiModelProperty(value = "사용자 이름", required = true)	
	private String name;
	@ApiModelProperty(value = "사용자 주소", required = true)	
	private String address;
	@ApiModelProperty(value = "사용자 이메일", required = true)	
	private String email;
	@ApiModelProperty(value = "사용자 휴대폰번호", required = true)	
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
