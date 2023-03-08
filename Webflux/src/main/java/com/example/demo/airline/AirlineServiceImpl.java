package com.example.demo.airline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AirlineMapper;

//AirportService에서 생성한 기능을 구현하는 공간 : 구현체
@Service
public class AirlineServiceImpl implements AirlineService {

	@Autowired
	private AirlineMapper airlineMapper;

	@Override
	public AirlineVO selectAirline(AirlineVO vo) throws Exception {
		return airlineMapper.selectAirline(vo);
	}

	@Override
	public int insertAirline(AirlineVO vo) throws Exception {
		return airlineMapper.insertAirline(vo);
	}

	@Override
	public int updateAirline(AirlineVO vo) throws Exception {
		return airlineMapper.updateAirline(vo);
	}

	@Override
	public int deleteAirline(AirlineVO vo) throws Exception {
		return airlineMapper.deleteAirline(vo);
	}


}
