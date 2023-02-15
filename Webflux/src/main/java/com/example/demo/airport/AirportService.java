package com.example.demo.airport;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

public interface AirportService {
	//CRUD(Create, Read,  Update, Delete)
	public abstract List<ListVO> airport_list();//공항조회
	
}
