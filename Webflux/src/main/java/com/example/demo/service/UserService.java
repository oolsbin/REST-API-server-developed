package com.example.demo.service;

import com.example.demo.vo.TokenVO;
import com.example.demo.vo.UserInfoVO;
import com.example.demo.vo.UserVO;

public interface UserService {
	int join(UserVO vo) throws Exception;
	String userPw(UserVO vo) throws Exception;
	String userId(UserVO vo) throws Exception;
	UserInfoVO userInfo(String id) throws Exception;
	UserVO login(UserVO vo) throws Exception;
	int refreshToken(TokenVO vo) throws Exception;
	TokenVO refreshTokenChk(TokenVO vo) throws Exception;
	int refreshTokenDelete(String id) throws Exception;
	String refreshTokenIdChk(String id) throws Exception;//token id중복값 확인
}