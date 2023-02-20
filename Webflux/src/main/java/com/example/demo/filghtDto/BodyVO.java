package com.example.demo.filghtDto;

import javax.xml.bind.annotation.XmlRootElement;

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
//@NoArgsConstructor
@XmlRootElement(name = "body") 
public class BodyVO {
	private ItemsVO items;
	
	private String numOfRows;
	private String pageNo;
	private String totalCount;
}
