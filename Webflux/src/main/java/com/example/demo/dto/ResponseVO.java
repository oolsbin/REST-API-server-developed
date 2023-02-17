package com.example.demo.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.StreamingHttpOutputMessage.Body;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Header;

@ToString
@Getter
@Setter
public class ResponseVO {
	private Header header;
	private Body body;
}
