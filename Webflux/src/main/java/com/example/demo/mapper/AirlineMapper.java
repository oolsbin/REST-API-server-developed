package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.airline.AirlineVO;
import com.example.demo.flight.FlightVO;

@Mapper
public interface AirlineMapper {
	AirlineVO selectAirline(AirlineVO vo) throws Exception;// airline 조회
	int insertAirline(AirlineVO vo) throws Exception;//airline 저장
	int updateAirline(AirlineVO vo) throws Exception;//airline 수정
	int deleteAirline(AirlineVO vo) throws Exception;//airline 삭제

}
