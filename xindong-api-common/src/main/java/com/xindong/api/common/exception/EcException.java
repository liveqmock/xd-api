package com.xindong.api.common.exception;

public class EcException extends Exception {
	private String code;
	private String message;
	
	public EcException(){}
	
	public EcException(String code, String message){
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		
		this.message = message;
	}
}
