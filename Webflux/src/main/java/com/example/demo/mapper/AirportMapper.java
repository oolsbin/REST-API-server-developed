package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.airport.AirportVO;

@Mapper
public interface AirportMapper {
	AirportVO seletAirport(AirportVO vo) throws Exception;//airport 조회
	int insertAirport(AirportVO vo) throws Exception;//airport 저장
	int updateAirport(AirportVO vo) throws Exception;//airport 수정
	int deleteAirport(AirportVO vo) throws Exception;//airport 삭제

}
