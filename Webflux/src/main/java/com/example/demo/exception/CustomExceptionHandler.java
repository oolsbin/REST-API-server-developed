//package com.example.demo.exception;
//
//import java.io.Serializable;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(CustomException.class)
//    public CustomErrorResponse handleException(
//    		CustomException e,
//    		HttpServletRequest requset
//	) {
//    	log.error("errorCode : {}, url {}, message: {}");
//    			e.getCu
//    			requset.
//    	
//		return CustomErrorResponse.builder()
//				.status(e.getCus);
//    	
//    }
//    
//}