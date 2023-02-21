package com.example.demo.airport;

import java.net.URI;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.airlinedto.ListVO;

//AirportService에서 생성한 기능을 구현하는 공간 : 구현체
@Service
public abstract class AirportServiceImpl implements AirportService {

	  public String getName(){
	        URI uri = UriComponentsBuilder
	                .fromUriString("http://localhost:8080")
	                .path("/api/v1/crud-api")
	                .encode()
	                .build()
	                .toUri();

	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

	        return responseEntity.getBody();
	    }
	    
	    public String getNameWithPathVariable(){
	        URI uri = UriComponentsBuilder
	                .fromUriString("http://localhost:8080")
	                .path("/api/v1/crud-api/{name}")
	                .encode()
	                .build()
	                .expand("Flature") // 복수의 값을 넣어야 할 경우 ,를 추가하여 구분
	                .toUri();
	        
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
	        
	        return responseEntity.getBody();
	    }
	    
	    public String getNameWithParameter(){
	        URI uri = UriComponentsBuilder
	                .fromUriString("http://localhost:8080")
	                .path("/api/v1/crud-api/param")
	                .queryParam("name", "Flature")
	                .encode()
	                .build()
	                .toUri();
	        
	        RestTemplate restTemplate = new RestTemplate();
	        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
	        
	        return responseEntity.getBody();
	    }
}
