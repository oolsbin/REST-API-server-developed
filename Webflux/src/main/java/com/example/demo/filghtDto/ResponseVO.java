package com.example.demo.filghtDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.StreamingHttpOutputMessage.Body;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import springfox.documentation.service.Header;


@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name="response")  
public class ResponseVO {
	
	@XmlElement(name = "header") 
	private HeaderVO header;
	@XmlElement(name= "body")  
	private BodyVO body;
}
