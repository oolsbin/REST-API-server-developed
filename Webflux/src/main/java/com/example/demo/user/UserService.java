package com.example.demo.user;

import com.example.demo.refresh.TokenVO;

public interface UserService {
	int join(UserVO vo) throws Exception;
	String userPw(UserVO vo) throws Exception;
	String userId(UserVO vo) throws Exception;
	UserVO login(UserVO vo) throws Exception;
	int refreshToken(TokenVO vo) throws Exception;
	TokenVO refreshToken_chk(TokenVO vo) throws Exception;
	int refreshToken_delete(TokenVO vo) throws Exception;
	String refreshToken_id_chk(String id) throws Exception;//token id중복값 확인
}