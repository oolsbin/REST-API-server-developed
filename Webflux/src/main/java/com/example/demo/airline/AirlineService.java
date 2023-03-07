package com.example.demo.airline;

//인터페이스
public interface AirlineService {
	int insertAirline(AirlineVO vo) throws Exception;//공항정보 저장
	AirlineVO selectAirline(AirlineVO vo) throws Exception;// DB테스트 조회
	
//	public void insertList_insert(ListVO vo);//입력받은데이터 db저장
	
	
	

	
}
