package com.cs.orderbook.service;

import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.exceptions.OrderBookException;

public interface IOrderBookExecutor {

	public boolean execute(ExecutionRequest executionRequest) throws OrderBookException;
	
}
