package com.example.demo.airportdto;

import java.util.List;
import javax.annotation.Generated;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import io.swagger.models.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ListVO {
	
	private ResponseVO response;
	
}