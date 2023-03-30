//package com.example.demo.msg;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//
//@AllArgsConstructor
//@Builder
//public class DefaultRes1 {// 상태코드, 응답메세지, 데이터로 형식을 갖춰서 클라이언트에게 응답을 해주기 위해서 DefaultRes 클래스를 만들었다.
//	 private int statusCode;
//	    private String responseMessage;
////	    private T data;
//
//	    public DefaultRes1(final int statusCode, final String responseMessage) {
//	        this.statusCode = statusCode;
//	        this.responseMessage = responseMessage;
////	        this.data = null;
//	    }
//
//	    public static DefaultRes1 res(final int statusCode, final String responseMessage) {
//	        return res(statusCode, responseMessage);
//	    }
//
//	    public staticDefaultRes res(final int statusCode, final String responseMessage) {
//	        return DefaultRes1.<T>builder()
////	                .data(t)
//	                .statusCode(statusCode)
//	                .responseMessage(responseMessage)
//	                .build();
//	    }
//}
