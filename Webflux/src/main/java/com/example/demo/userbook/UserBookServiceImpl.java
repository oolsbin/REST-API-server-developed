package com.example.demo.userbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.flight.FlightVO;
import com.example.demo.mapper.UserBookMapper;
import com.example.demo.user.UserVO;
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
	public int economyCnt(String flightId) throws Exception {
		return userbookmapper.economyCnt(flightId);
	}

	@Override
	public int prestigeCnt(String flightId) throws Exception {
		return userbookmapper.prestigeCnt(flightId);
	}

	@Override
	public int insertUserBook(SeatVO vo) throws Exception {
		return userbookmapper.insertUserBook(vo);
	}

//	@Override
//	public UserVO economySeatCnt(UserVO vo) throws Exception {
//		return userbookmapper.UserInfo(vo);
//	}
//
//	@Override
//	public int prestigeSeatCnt() throws Exception {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public UserVO UserInfo(UserVO vo) throws Exception {
		return userbookmapper.UserInfo(vo);
	}

	@Override
	public FlightVO FlightInfo(FlightVO vo) throws Exception {
		return userbookmapper.FlightInfo(vo);
	}
}
