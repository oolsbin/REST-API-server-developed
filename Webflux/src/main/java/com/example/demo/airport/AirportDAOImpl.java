package com.example.demo.airport;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AirportDAOImpl implements AirportDAO{

	@Override
	public List<ListVO> airport_list(SqlSessionTemplate session) {
		return session.selectList("airport.List");
	}
}
