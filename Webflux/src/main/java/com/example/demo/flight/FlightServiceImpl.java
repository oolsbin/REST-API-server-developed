package com.example.demo.flight;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.flightdto.BodyVO;
import com.example.demo.dto.flightdto.ItemVO;
import com.example.demo.mapper.FlightMapper;
import com.example.demo.vo.SeatVO;

@Service
public class FlightServiceImpl implements FlightService{
	
	@Autowired
	private FlightMapper flightmapper;
	
	//client에서 flag를 받아 여기서 다 처리한다
	@Override
	public int insertFlight(List<FlightVO> voList) throws Exception{
		int result = 0;
		for(FlightVO vo : voList) {
			if("add".equals(vo.getFlag())) {
				result += flightmapper.insertFlight(vo);
			}else if("update".equals(vo.getFlag())) {
				result += flightmapper.updateFlight(vo);
			}else if("delete".equals(vo.getFlag())) {
				result += flightmapper.deleteFlight(vo);
			}
		}
		return result;
	}

	
	@Override
	public int updateFlight(FlightVO vo) throws Exception {
		return flightmapper.updateFlight(vo);
	}

	@Override
	public int deleteFlight(FlightVO vo) throws Exception {
		return flightmapper.deleteFlight(vo);
	}


	@Override
	public List<FlightVO> find(Map<String, Object> vo) throws Exception {
		return flightmapper.find(vo);
	}
	
	@Override
	public List<FlightVO> findAll(Map<String, Object> vo) throws Exception {
		return flightmapper.find(vo);
	}


	@Override
	public CountVO total(Map<String, Object> vo) throws Exception {
		return flightmapper.total(vo);
	}


	@Override
	public int seatInsert(SeatVO vo) throws Exception {
		return flightmapper.seatInsert(vo);
	}


	@Override
	public String airlineNm(String airlineId) throws Exception {
		return flightmapper.airlineNm(airlineId);
	}


	@Override
	public String airportNm(String airportNm) throws Exception {
		return flightmapper.airportNm(airportNm);
	}


	@Override
	public List<FlightVO> findAirline(Map<String, Object> vo) throws Exception {
		return flightmapper.findAirline(vo);
	}
	

	// 페이지 별 회원 조회
//	public List<BodyVO> findMembers(int page) {
//	    return memberRepository.findAll(page);
//	}
//
//	// 페이지의 개수
//	public int[] pageList() {
//	    int totalPage = memberRepository.boardCount() / 10;
//	    totalPage = ((memberRepository.boardCount() % 10) == 0) ? totalPage : totalPage + 1;
//
//	    int[] pages = new int[totalPage];
//	    for(int i = 0; i < totalPage; i++) {
//	        pages[i] = i + 1;
//	    }
//
//	    return pages;
//	}
}
