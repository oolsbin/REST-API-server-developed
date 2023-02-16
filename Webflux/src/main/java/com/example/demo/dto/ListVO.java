package com.example.demo.dto;

import java.util.List;
import javax.annotation.Generated;

import org.apache.ibatis.type.Alias;

import io.swagger.models.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ListVO {

	private String airlineId, airlineNm;

	public ListVO(String airlineId, String airlineNm) {
		super();
		this.airlineId = airlineId;
		this.airlineNm = airlineNm;
	}

	public String getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}

	public String getAirlineNm() {
		return airlineNm;
	}

	public void setAirlineNm(String airlineNm) {
		this.airlineNm = airlineNm;
	}

	private Response response;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}