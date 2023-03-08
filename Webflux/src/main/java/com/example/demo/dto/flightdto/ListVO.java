package com.example.demo.dto.flightdto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
//@JsonAutoDetect
public class ListVO {

	private ResponseVO response;

}