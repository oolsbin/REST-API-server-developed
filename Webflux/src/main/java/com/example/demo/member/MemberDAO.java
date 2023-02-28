//package com.example.demo.member;
//
//import java.util.HashMap;
//
//import org.apache.ibatis.session.SqlSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class MemberDAO implements MemberService{
//	
//
//	@Override
//	public MemberVO login_login(String id, String pw) throws Exception{
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("id", id);
//		map.put("pw", pw);
//		return sql.selectOne("member.login", map);
//	}
//}