package com.example.demo.airport;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportServiceImpl implements AirportService {
	
	@Autowired
	AirportDAO dao;
	
	@Autowired
	SqlSessionTemplate session;
	
	@Override
	public List<ListVO> airport_list() {
		return dao.airport_list(session);
	}
}
