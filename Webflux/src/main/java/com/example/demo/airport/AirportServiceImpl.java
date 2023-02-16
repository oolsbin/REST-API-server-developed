package com.example.demo.airport;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl implements AirportService {
	
	@Autowired
	private AirportMapper airportMapper;
	
		@Override
		public String selectTest() {
			return airportMapper.selectTest();
		}
}
