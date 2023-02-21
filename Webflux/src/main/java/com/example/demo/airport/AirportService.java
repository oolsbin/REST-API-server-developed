package com.example.demo.airport;

import java.net.URI;
import java.nio.charset.CharacterCodingException;
import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.airlinedto.ItemVO;
import com.example.demo.airlinedto.ItemsVO;
import com.example.demo.airlinedto.ListVO;

//인터페이스
public interface AirportService {
	public String selectTest();// DB테스트 조회
//	public void insertList_insert(ListVO vo);//입력받은데이터 db저장
	
	

	
}
