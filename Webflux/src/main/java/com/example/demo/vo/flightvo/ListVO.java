package com.example.demo.vo.flightvo;

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