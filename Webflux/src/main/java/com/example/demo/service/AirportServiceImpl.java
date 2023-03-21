package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AirportMapper;
import com.example.demo.vo.AirportVO;

//AirportService에서 생성한 기능을 구현하는 공간 : 구현체
@Service
public class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportMapper airportMapper;

	@Override
	public AirportVO seletAirport(AirportVO vo) throws Exception {
		return airportMapper.seletAirport(vo);
	}

	@Override
	public int insertAirport(AirportVO vo) throws Exception {
		return airportMapper.insertAirport(vo);
	}

	@Override
	public int updateAirport(AirportVO vo) throws Exception {
		return airportMapper.updateAirport(vo);
	}

	@Override
	public int deleteAirport(AirportVO vo) throws Exception {
		return airportMapper.deleteAirport(vo);
	}
	
	
	
}
