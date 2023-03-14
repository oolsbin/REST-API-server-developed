package com.example.demo.userbook;

import java.util.List;

import com.example.demo.vo.UserBookVO;
import com.example.demo.vo.SeatVO;

public interface UserBookService {
//	int insertSeat(List<????> vo) throw Exception;
	List<UserBookVO> selectUserBook(UserBookVO vo) throws Exception;
	List<SeatVO> seatList(SeatVO vo) throws Exception;
	int economyCnt() throws Exception;//이코노미 좌석개수
	int prestigeCnt() throws Exception;//비즈니스 좌석개수
	int insertUserBook(SeatVO vo) throws Exception;//예약저장
}
