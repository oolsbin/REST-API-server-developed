package com.example.demo.flight;

import java.util.List;
import java.util.Map;

import com.example.demo.dto.flightdto.BodyVO;
import com.example.demo.dto.flightdto.ItemVO;
import com.example.demo.refresh.TokenVO;
import com.example.demo.vo.SeatVO;

public interface FlightService {
	List<FlightVO> find(Map<String, Object> vo) throws Exception;
	int insertFlight(List<FlightVO> voList) throws Exception;//flight 응답값 저장
	int updateFlight(FlightVO vo) throws Exception; //flight 수정하여 저장
	int deleteFlight(FlightVO vo) throws Exception;//flight 삭제
	CountVO total() throws Exception;//t
	int seatInsert(SeatVO vo) throws Exception;//좌석저장
}
