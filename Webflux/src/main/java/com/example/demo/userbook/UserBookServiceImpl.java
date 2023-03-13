package com.example.demo.userbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserBookMapper;
import com.example.demo.vo.UserBookVO;

@Service
public class UserBookServiceImpl implements UserBookService{
	
	@Autowired
	private UserBookMapper userbookmapper;
	
//	@Override
//	public int insertSeat(List<???> vo) throws Exception {
//		return userbookmapper.insertSeat(vo);
//	}
	
	@Override
	public List<UserBookVO> selectUserBook(UserBookVO vo) throws Exception {
		return userbookmapper.selectUserBook(vo);
	}

	@Override
	public List<UserBookVO> seatCnt(UserBookVO vo) throws Exception {
		return userbookmapper.seatCnt(vo);
	}
}
