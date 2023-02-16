package com.example.demo.airport;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ListVO;

//AirportService에서 생성한 기능을 구현하는 공간 : 구현체
@Service
public abstract class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportMapper airportMapper;

	@Override
	public String selectTest() {
		return airportMapper.selectTest();
	}

//	@Override
//	public void insertList_insert(ListVO vo) {
//		return;
//	}
}
