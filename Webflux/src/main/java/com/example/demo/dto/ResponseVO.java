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

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
