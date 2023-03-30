package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.TokenVO;
import com.example.demo.vo.UserInfoVO;
import com.example.demo.vo.UserVO;

@Mapper
public interface UserMapper {
	UserVO login(UserVO vo) throws Exception;//로그인
	int refreshToken(TokenVO vo) throws Exception;//refreshToken 저장
	int join(UserVO vo) throws Exception;//회원가입
	String userPw(UserVO vo) throws Exception;
	String userId(UserVO vo) throws Exception;//user Id 리스트
	UserInfoVO userInfo(String id) throws Exception;//user Id 리스트
	TokenVO refreshTokenChk(TokenVO vo) throws Exception;//refreshToken check
	int refreshTokenDelete(String id) throws Exception;//refreshToken check
	String refreshTokenIdChk(String id) throws Exception;//token id중복값 확인
}