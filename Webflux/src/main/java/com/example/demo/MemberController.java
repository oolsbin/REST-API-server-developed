//package com.example.demo;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.member.MemberService;
//import com.example.demo.member.MemberServiceImpl;
//import com.example.demo.member.MemberVO;
//
//@RestController
//public class MemberController {
//
//	@Resource(name="MemberService")
////	@Autowired
//	private MemberService service;
//	
//	// 로그인 처리
//	@PostMapping(value = "/login")
//	public String Login(String id, String pw, HttpSession session) throws Exception {
//
//		MemberVO vo = service.login(id, pw);
//
//		session.setAttribute("loginInfo", vo);
//		if (vo == null)
//			return "mainlogin";
//		else
//			return "redirect:/";
//	}
//	
//}
