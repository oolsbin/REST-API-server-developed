package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.airport.AirportVO;
import com.example.demo.dto.airlinedto.ListVO;
import com.example.demo.flight.FlightVO;
import com.example.demo.user.UserVO;

@Mapper
public interface AirportMapper {
	AirportVO seletAirport(AirportVO vo) throws Exception;//airport 조회
	int insertAirport(AirportVO vo) throws Exception;//airport 저장
	int updateAirport(AirportVO vo) throws Exception;//airport 수정
	int deleteAirport(AirportVO vo) throws Exception;//airport 삭제

}
