//package com.example.demo;
//
//import java.util.Locale.Category;
//
//import io.swagger.annotations.Api;
//import lombok.Builder;
//
//public class ApiResponse {
//	private String airlineID;
//	private String airlineNm;
//	
//	@Builder
//	protected ApiResponse(String airlineID, String airlineNm) {
//		this.airlineID = airlineID;
//		this.airlineNm = airlineNm;
//	}
//	
//	private static ApiResponseBuilder fromCommon(Api api) {
//		Category category = api.getCategory();
//		CategoryResponse.api 
//	}
//	
//	public static ApiResponse from(Api api) {
//		return fromCommon(api)
//				.content(api.getContent())
//				.build();
//	}
//
//}
