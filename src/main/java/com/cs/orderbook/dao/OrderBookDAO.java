package com.cs.orderbook.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cs.orderbook.beans.OrderBook;
import com.cs.orderbook.exceptions.OrderBookException;

@Repository
public class OrderBookDAO {

	private Map<String, OrderBook> orderBookMap = new HashMap<>();  
	
	
	public OrderBook getOrderBook(String instrumentId) {
    	return orderBookMap.get(instrumentId);    	
    }
	
	 synchronized public void openOrderBook(String instrumentId) throws OrderBookException{
	        if(!orderBookMap.containsKey(instrumentId)){
	            OrderBook orderBook = new OrderBook(instrumentId);
	            orderBookMap.put(instrumentId, orderBook);
	           
	        }else{
	        	throw new OrderBookException(OrderBookException.ORDER_BOOK_ALREADY_OPENED);
	        }       
	 }
	 
	
	
}
