package com.example.demo.userbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserBookMapper;
import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.SeatVO;

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
	public List<SeatVO> seatList(SeatVO vo) throws Exception {
		return userbookmapper.seatList(vo);
	}

	@Override
	public int economyCnt() throws Exception {
		return userbookmapper.economyCnt();
	}

	@Override
	public int prestigeCnt() throws Exception {
		return userbookmapper.prestigeCnt();
	}

	@Override
	public int insertUserBook(SeatVO vo) throws Exception {
		return userbookmapper.insertUserBook(vo);
	}
}
