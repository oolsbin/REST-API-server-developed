package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserBookMapper;
import com.example.demo.vo.FlightVO;
import com.example.demo.vo.SeatVO;
import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.UserInfoVO;

@Service
public class UserBookServiceImpl implements UserBookService{
	
	@Autowired
	private UserBookMapper userbookmapper;
	
//	@Override
//	public int insertSeat(List<???> vo) throws Exception {
//		return userbookmapper.insertSeat(vo);
//	}
	
	@Override
	public List<UserBookVO> selectUserBook(String id) throws Exception {
		return userbookmapper.selectUserBook(id);
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
	public UserInfoVO userInfo(String userId) throws Exception {
		return userbookmapper.userInfo(userId);
	}

	@Override
	public FlightVO flightInfo(String flightId) throws Exception {
		return userbookmapper.flightInfo(flightId);
	}

	@Override
	public List<UserBookVO> chargeSum(String id) throws Exception {
		return userbookmapper.selectUserBook(id);
	}

	@Override
	public int deleteUserBook(String reservationId) throws Exception {
		return userbookmapper.deleteUserBook(reservationId);
	}
}
