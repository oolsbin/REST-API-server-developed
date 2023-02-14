package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//^이 설정은 스프링 컨텍스트가 로딩될 때 랜덤한 포트를 사용하게 설정해주는 것으로,
//나중에 서버가 떠있는 상태에서 테스트를 돌리다가 충돌나는 케이스를 방지하기 위한 것이다.
class WebfluxApplicationTests {

	@Test
	void contextLoads() {
	}

}
