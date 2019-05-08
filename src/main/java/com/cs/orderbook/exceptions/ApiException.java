package com.cs.orderbook.exceptions;

public class ApiException extends Exception{

	private static final long serialVersionUID = 8473459246093896576L;

	public String getErrorCode() {
		return "API_ERROR";
	}
	
}
