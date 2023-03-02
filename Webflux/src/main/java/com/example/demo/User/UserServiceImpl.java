package com.example.demo.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper usermapper;
	
	@Override
	public int join(UserVO vo) {
		return usermapper.join(vo);
	}
}
