package com.example.demo.dto;

import java.util.List;
import javax.annotation.Generated;

import org.apache.ibatis.type.Alias;

import io.swagger.models.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//응답객체
//@AllArgsConstructor
//@NoArgsConstructor
@ToString
@Getter
@Setter
public class ListVO {

	private String airlineId;
	private String airlineNm;
	
	private ResponseVO response;
	
//	@Override
//	public String toString() {
//		StringBuffer result = new StringBuffer();
//		for(String i : this.airlineId) {
//			result.append(", ").append(i);
//		}
//		return result.toString();
//	}
}