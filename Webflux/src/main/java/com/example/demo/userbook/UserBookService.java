package com.example.demo.userbook;

import java.util.List;

import com.example.demo.vo.UserBookVO;

public interface UserBookService {
//	int insertSeat(List<????> vo) throw Exception;
	List<UserBookVO> selectUserBook(UserBookVO vo) throws Exception;
	List<UserBookVO> seatCnt(UserBookVO vo) throws Exception;
}
