package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.UserBookVO;

@Mapper
public interface UserBookMapper {
//	int insertSeat(??? vo) throws Exception;
	List<UserBookVO> selectUserBook(UserBookVO vo) throws Exception;
	List<UserBookVO> seatCnt(UserBookVO vo) throws Exception;
}
