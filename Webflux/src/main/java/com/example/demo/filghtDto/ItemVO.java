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
@XmlRootElement(name="item")  
public class ItemVO {
	private String airlineNm;
	private String arrAirportNm;
	private String arrPlandTime;
	private String depAirportNm;
	private String depPlandTime;
	private String economyCharge;
	private String prestigeCharge;
	private String vihicleId;
}
