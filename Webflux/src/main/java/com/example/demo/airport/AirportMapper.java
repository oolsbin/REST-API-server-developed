package com.example.demo.airport;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
//@Repository
public interface AirportMapper {
	String selectTest();//공항조회
}
