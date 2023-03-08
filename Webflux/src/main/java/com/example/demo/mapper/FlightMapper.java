package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.dto.flightdto.ItemVO;
import com.example.demo.flight.FlightVO;

@Mapper
public interface FlightMapper {

	int insertFlight(FlightVO vo) throws Exception;//flight 응답값 저장
	int updateFlight(FlightVO vo) throws Exception;//flight 조회
	int deleteFlight(FlightVO vo) throws Exception;//flight 삭제
}
