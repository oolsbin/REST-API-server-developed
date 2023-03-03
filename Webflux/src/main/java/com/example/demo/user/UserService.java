package com.example.demo.user;

import java.util.Map;

public interface UserService {
	int join(UserVO vo);
	int login(UserVO vo) throws Exception;
}