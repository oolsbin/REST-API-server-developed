package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.flight.FlightVO;
import com.example.demo.vo.SeatVO;
import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.UserInfoVO;

@Mapper
public interface UserBookMapper {
//	int insertSeat(??? vo) throws Exception;
	List<UserBookVO> selectUserBook(String id) throws Exception;
	List<UserBookVO> ChargeSum(String id) throws Exception;
	List<SeatVO> seatList(SeatVO vo) throws Exception;
	int economyCnt(String flightId)throws Exception;//이코노미 좌석개수
	int prestigeCnt(String flightId) throws Exception;//비즈니스 좌석개수
	int insertUserBook(SeatVO vo) throws Exception;//예약저장
	//int economySeatCnt() throws Exception;//이코노미 좌석번호 최댓값
	//int prestigeSeatCnt() throws Exception;//비즈니스 좌석번호 최댓값
	UserInfoVO UserInfo(String userId) throws Exception;
	FlightVO FlightInfo(String flightId) throws Exception;
}
