package com.example.demo.userbook;

import java.util.List;

import com.example.demo.vo.UserBookVO;
import com.example.demo.flight.FlightVO;
import com.example.demo.user.UserVO;
import com.example.demo.vo.SeatVO;

public interface UserBookService {
//	int insertSeat(List<????> vo) throw Exception;
	List<UserBookVO> selectUserBook(UserBookVO vo) throws Exception;
	List<SeatVO> seatList(SeatVO vo) throws Exception;
	int economyCnt(String flightId) throws Exception;//이코노미 좌석개수
	int prestigeCnt(String flightId) throws Exception;//비즈니스 좌석개수
	int insertUserBook(SeatVO vo) throws Exception;//예약저장
//	int economySeatCnt() throws Exception;//이코노미 좌석번호 최댓값
//	int prestigeSeatCnt() throws Exception;//비즈니스 좌석번호 최댓값
	UserVO UserInfo(String userId) throws Exception;
	FlightVO FlightInfo(String flightId) throws Exception;
}
