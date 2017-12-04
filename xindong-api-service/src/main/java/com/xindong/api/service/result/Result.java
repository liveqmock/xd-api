package com.xindong.api.service.result;

import java.io.Serializable;

public class Result implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Boolean success = false;
	private String resultCode = "";
	private String resultMessage = "";
	private Object result;
	
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
