package com.example.demo.service;

import java.util.List;

import com.example.demo.vo.FlightVO;
import com.example.demo.vo.SeatVO;
import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.UserInfoVO;

public interface UserBookService {
//	int insertSeat(List<????> vo) throw Exception;
	List<UserBookVO> selectUserBook(String id) throws Exception;
	List<UserBookVO> chargeSum(String id) throws Exception;
	List<SeatVO> seatList(SeatVO vo) throws Exception;
	int economyCnt(String flightId) throws Exception;//이코노미 좌석개수
	int prestigeCnt(String flightId) throws Exception;//비즈니스 좌석개수
	int insertUserBook(SeatVO vo) throws Exception;//예약저장
//	int economySeatCnt() throws Exception;//이코노미 좌석번호 최댓값
//	int prestigeSeatCnt() throws Exception;//비즈니스 좌석번호 최댓값
	UserInfoVO userInfo(String userId) throws Exception;
	FlightVO flightInfo(String flightId) throws Exception;
	int deleteUserBook(String reservationId)throws Exception;
}
