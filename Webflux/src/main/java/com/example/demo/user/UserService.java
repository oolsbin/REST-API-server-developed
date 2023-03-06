package com.example.demo.user;

import java.util.Map;

import com.example.demo.refresh.TokenVO;

public interface UserService {
	int join(UserVO vo) throws Exception;
	UserVO login(UserVO vo) throws Exception;
	int userId(UserVO vo) throws Exception;
}