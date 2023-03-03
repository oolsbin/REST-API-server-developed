package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.UserVO;

@Mapper
public interface UserMapper {
	UserVO login(UserVO vo);//로그인
	int join(UserVO vo);//회원가입
}