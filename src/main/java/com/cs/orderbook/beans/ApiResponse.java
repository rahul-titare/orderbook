package com.cs.orderbook.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiResponse {

	private String status;
	
	@JsonInclude(Include.NON_NULL)
	private String errorCode;
	@JsonInclude(Include.NON_NULL)
	private Object data;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public void success() {
		status = "success";
	}
	
	public void error(String errorCode) {
		status = "error";
		this.errorCode = errorCode;
	}
	
	
}
