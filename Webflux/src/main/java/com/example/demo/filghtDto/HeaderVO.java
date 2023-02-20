package com.example.demo.filghtDto;

import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement(name = "header")  
public class HeaderVO {
	private String resultCode;
	private String resultMsg;
}
