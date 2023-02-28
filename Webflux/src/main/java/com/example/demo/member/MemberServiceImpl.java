//package com.example.demo.member;
//
//import java.util.HashMap;
//
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MemberServiceImpl implements MemberService{
//	@Autowired private MemberMapper mapper;
//
//	@Override
//	public MemberVO login(String id, String pw) throws Exception{
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", id);
//		map.put("pw", pw);
//		return mapper.Login(map);
//	}
//}
