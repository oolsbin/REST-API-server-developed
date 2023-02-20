package com.example.demo.dto;

import org.springframework.http.StreamingHttpOutputMessage.Body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Header;


@Getter
@Setter
public class AirportVO {

	private String airportId;
	private String airportNm;
	
}
