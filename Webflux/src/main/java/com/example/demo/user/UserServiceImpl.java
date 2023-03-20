package com.example.demo.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	public String userId(UserVO vo) throws Exception {
		return usermapper.userId(vo);
	}

	@Override
	public String userPw(UserVO vo) throws Exception {
		return usermapper.userPw(vo);
	}
	
	@Override
	public int refreshToken(TokenVO vo) throws Exception {
		return usermapper.refreshToken(vo);
	}

	@Override
	public TokenVO refreshToken_chk(TokenVO vo) throws Exception {
		return usermapper.refreshToken_chk(vo);
	}
	
	@Override
	public int refreshToken_delete(TokenVO vo) throws Exception {
		return usermapper.refreshToken_delete(vo);
	}


	@Override
	public String refreshToken_id_chk(String id) throws Exception {
		return usermapper.refreshToken_id_chk(id);
	}


	

}
