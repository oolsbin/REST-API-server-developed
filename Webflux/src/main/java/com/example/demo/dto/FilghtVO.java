package com.example.demo.dto;

import org.springframework.http.StreamingHttpOutputMessage.Body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Header;

@Getter
@Setter
public class FilghtVO {
	
	private String depAirportId; //출발공항ID
	private String arrAirportId; //도착공항ID
	private String depPlandTime; //출발일(YYYYMMDD)
	private String airlineId;	//항공사ID

}
