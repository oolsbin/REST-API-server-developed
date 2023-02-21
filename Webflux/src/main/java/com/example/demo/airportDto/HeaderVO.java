package com.example.demo.airportdto;

import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Header;

@Getter
@Setter
public class HeaderVO {
	private String resultCode;
	private String resultMsg;
}
