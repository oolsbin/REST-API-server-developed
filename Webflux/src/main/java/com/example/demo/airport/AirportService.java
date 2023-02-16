package com.example.demo.airport;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ListVO;

//인터페이스
public interface AirportService {
	public String selectTest();// DB테스트 조회
	public void insertList_insert(ListVO vo);
}
