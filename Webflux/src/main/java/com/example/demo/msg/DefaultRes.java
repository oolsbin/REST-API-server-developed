package com.example.demo.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DefaultRes<T> {// 상태코드, 응답메세지, 데이터로 형식을 갖춰서 클라이언트에게 응답을 해주기 위해서 DefaultRes 클래스를 만들었다.
	 private int statusCode;
	    private String responseMessage;
	    private T data;

	    public DefaultRes(final int statusCode, final String responseMessage) {
	        this.statusCode = statusCode;
	        this.responseMessage = responseMessage;
	        this.data = null;
	    }

	    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage) {
	        return res(statusCode, responseMessage, null);
	    }

	    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T t) {
	        return DefaultRes.<T>builder()
	                .data(t)
	                .statusCode(statusCode)
	                .responseMessage(responseMessage)
	                .build();
	    }
}
