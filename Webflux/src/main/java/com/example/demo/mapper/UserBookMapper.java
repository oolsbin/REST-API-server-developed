package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.SeatVO;

@Mapper
public interface UserBookMapper {
//	int insertSeat(??? vo) throws Exception;
	List<UserBookVO> selectUserBook(UserBookVO vo) throws Exception;
	List<SeatVO> seatList(SeatVO vo) throws Exception;
	int economyCnt()throws Exception;//이코노미 좌석개수
	int prestigeCnt() throws Exception;//비즈니스 좌석개수
	int insertUserBook(SeatVO vo) throws Exception;//예약저장
}
