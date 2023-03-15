package com.example.demo.user;

import java.util.Map;

import com.example.demo.refresh.TokenVO;
import com.example.demo.vo.SeatVO;

public interface UserService {
	int join(UserVO vo) throws Exception;
	String userPw(UserVO vo) throws Exception;
	String userId(UserVO vo) throws Exception;
	UserVO login(UserVO vo) throws Exception;
	int refreshToken(TokenVO vo) throws Exception;
	TokenVO refreshToken_chk(TokenVO vo) throws Exception;
	int refreshToken_delete(TokenVO vo) throws Exception;
}