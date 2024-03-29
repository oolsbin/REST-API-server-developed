package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.CountVO;
import com.example.demo.vo.FlightVO;
import com.example.demo.vo.SeatVO;

@Mapper
public interface FlightMapper {
	List<FlightVO> find(Map<String, Object> vo) throws Exception;
	List<FlightVO> findAll(Map<String, Object> vo) throws Exception;
	List<FlightVO> findAirline(Map<String, Object> vo) throws Exception;
	List<FlightVO> response(Map<String, Object> vo) throws Exception;
	List<FlightVO> responseAirline(Map<String, Object> vo) throws Exception;
	int insertFlight(FlightVO vo) throws Exception;//flight 저장
	int updateFlight(FlightVO vo) throws Exception;//flight 수정
	int deleteFlight(FlightVO vo) throws Exception;//flight 삭제
	CountVO total(Map<String, Object> vo) throws Exception;//total count
	CountVO totalAirline(Map<String, Object> vo) throws Exception;//total count
	int seatInsert(SeatVO vo) throws Exception;//좌석저장
	String airlineNm(String airlineId) throws Exception;
	String airportNm(String airportNm) throws Exception;
	FlightVO flightUserBook(Map<String, Object> vo) throws Exception;
	FlightVO flightUserBookAirline(Map<String, Object> vo) throws Exception;
}
