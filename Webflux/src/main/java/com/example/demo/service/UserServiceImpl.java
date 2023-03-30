package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.TokenVO;
import com.example.demo.vo.UserInfoVO;
import com.example.demo.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserVO login(UserVO vo) throws Exception{
		return usermapper.login(vo);
	}
	
	
//	@Override
//	public String login_pw(UserVO vo){
//		return usermapper.login_pw(vo);
//	}

	
	@Override
	public int join(UserVO vo) throws Exception{
		String encodedPassword = passwordEncoder.encode(vo.getPw());
		vo.setPw(encodedPassword);
		
		return usermapper.join(vo);
	}


	@Override
	public String userId(UserVO vo) throws Exception {
		return usermapper.userId(vo);
	}

	@Override
	public String userPw(UserVO vo) throws Exception {
		return usermapper.userPw(vo);
	}
	
	@Override
	public UserInfoVO userInfo(String id) throws Exception {
		return usermapper.userInfo(id);
	}
	
	@Override
	public int refreshToken(TokenVO vo) throws Exception {
		return usermapper.refreshToken(vo);
	}

	@Override
	public TokenVO refreshTokenChk(TokenVO vo) throws Exception {
		return usermapper.refreshTokenChk(vo);
	}
	
	@Override
	public int refreshTokenDelete(String id) throws Exception {
		return usermapper.refreshTokenDelete(id);
	}

	@Override
	public String refreshTokenIdChk(String id) throws Exception {
		return usermapper.refreshTokenIdChk(id);
	}


	

}
