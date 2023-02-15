package com.example.demo.airport;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public interface AirportDAO {
	public abstract List<ListVO> airport_list(SqlSessionTemplate session);
}
