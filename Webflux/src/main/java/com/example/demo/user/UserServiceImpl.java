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

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public int login(UserVO vo) throws Exception{
		return usermapper.login(vo);
	}

	
	@Override
	public int join(UserVO vo) {

		String encodedPassword = passwordEncoder.encode(vo.getPw());
		vo.setPw(encodedPassword);
		//여기서 인코드 값을 어떻게 return... 시켜야 하나요...
		//인코드 시켜서 저장해야 하는데..
		return usermapper.join(vo);
	}
	

}
