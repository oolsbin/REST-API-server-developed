package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.refresh.TokenVO;
import com.example.demo.user.UserVO;

@Mapper
public interface UserMapper {
	UserVO login(UserVO vo) throws Exception;//로그인
	int join(UserVO vo) throws Exception;//회원가입
//	String login_pw(UserVO vo);
//	String refreshToken(TokenVO vo);//토큰 저장
}