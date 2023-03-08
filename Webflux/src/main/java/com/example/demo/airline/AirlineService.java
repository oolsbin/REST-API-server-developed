package com.example.demo.airline;

//인터페이스
public interface AirlineService {
	AirlineVO selectAirline(AirlineVO vo) throws Exception;// airline 조회
	int insertAirline(AirlineVO vo) throws Exception;//airline 저장
	int updateAirline(AirlineVO vo) throws Exception;//airline 수정
	int deleteAirline(AirlineVO vo) throws Exception;//airline 삭제

}
