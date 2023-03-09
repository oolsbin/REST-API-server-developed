package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.dto.flightdto.ItemVO;
import com.example.demo.flight.CountVO;
import com.example.demo.flight.FlightVO;
import com.example.demo.flight.page.SearchDto;

@Mapper
public interface FlightMapper {
	List<FlightVO> find(Map<String, Object> vo) throws Exception;
	
	int insertFlight(FlightVO vo) throws Exception;//flight 저장
	int updateFlight(FlightVO vo) throws Exception;//flight 수정
	int deleteFlight(FlightVO vo) throws Exception;//flight 삭제
	CountVO total() throws Exception;//total count
}
