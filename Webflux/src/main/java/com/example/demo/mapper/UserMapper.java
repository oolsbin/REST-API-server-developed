package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.refresh.TokenVO;
import com.example.demo.user.UserVO;

@Mapper
public interface UserMapper {
	UserVO login(UserVO vo) throws Exception;//로그인
	int refreshToken(TokenVO vo) throws Exception;//refreshToken 저장
	int join(UserVO vo) throws Exception;//회원가입
	String userPw(UserVO vo) throws Exception;
	String userId(UserVO vo) throws Exception;//user Id 리스트
	TokenVO refreshToken_chk(TokenVO vo) throws Exception;//refreshToken check
	int refreshToken_delete(TokenVO vo) throws Exception;//refreshToken check
	String refreshToken_id_chk(String id) throws Exception;//token id중복값 확인
}