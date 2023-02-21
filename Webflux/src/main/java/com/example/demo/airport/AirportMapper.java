package com.example.demo.airport;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.airlinedto.ListVO;

//Mapper 설정파일(xml)에 있는 SQL 쿼리문을 호출하기 위한 인터페이스를 생성한다.
//DAO대신 @Mapper어노테이션을 사용한다.(mybatis 3.0이상)
//Mapper어노테이션을 사용하면 빈으로 등록되어 service단에서 Autowired하여 사용할 수 있다.
//메소드 명은 Mapper xml파일의 id와 맞춰줘야 한다.
//@MapperScan어노테이션을 이용할 수도 있다. ex) @MapperScan(value="매퍼 인터페이스 경로")
@Mapper
public interface AirportMapper {
	String selectTest();// DB테스트 조회
//	void insertsList(ListVO vo);// 검색내용 저장

}
