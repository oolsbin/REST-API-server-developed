package com.example.demo.filghtDto;

import java.util.List;

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
@XmlRootElement(name = "items")
public class ItemsVO {
	private List<ItemVO> item;
	
	
}
