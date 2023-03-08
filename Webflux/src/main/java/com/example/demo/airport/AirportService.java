package com.example.demo.airport;

//인터페이스
public interface AirportService {
	AirportVO seletAirport(AirportVO vo) throws Exception;//airport 조회
	int insertAirport(AirportVO vo) throws Exception;//airport 저장
	int updateAirport(AirportVO vo) throws Exception;//airport 수정
	int deleteAirport(AirportVO vo) throws Exception;//airport 삭제
	
}
