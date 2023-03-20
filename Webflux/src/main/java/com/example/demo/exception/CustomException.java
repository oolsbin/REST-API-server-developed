package com.example.demo.exception;

public class CustomException extends RuntimeException {

    private CustomErrorCode errorCode;
    
	private String detaliMessage;

	public CustomException(CustomErrorCode errorCode, String detaliMessage) {
			super();
			this.errorCode = errorCode;
			this.detaliMessage = detaliMessage;
		}

	public CustomErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(CustomErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getDetaliMessage() {
		return detaliMessage;
	}

	public void setDetaliMessage(String detaliMessage) {
		this.detaliMessage = detaliMessage;
	}
	
	
}