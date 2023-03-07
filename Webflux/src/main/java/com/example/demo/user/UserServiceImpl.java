package com.example.demo.user;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.mapper.UserMapper;
import com.example.demo.refresh.TokenVO;

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
	public int userId(UserVO vo) throws Exception {
		return usermapper.userId(vo);
	}


	@Override
	public int refreshToken(TokenVO vo) throws Exception {
		return usermapper.refreshToken(vo);
	}

	@Override
	public TokenVO refreshToken_chk(TokenVO vo) throws Exception {
		return usermapper.refreshToken_chk(vo);
	}

}
