package com.cs.orderbook.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cs.orderbook.beans.OrderBook;
import com.cs.orderbook.beans.OrderBookStatus;
import com.cs.orderbook.exceptions.OrderBookException;

@Repository
public class OrderBookDAO {

	private Map<String, OrderBook> orderBookMap = new HashMap<>();  
	
	
	public OrderBook getOrderBook(String instrumentId) throws OrderBookException {
    	if(orderBookMap.containsKey(instrumentId)) {
    		return orderBookMap.get(instrumentId).clone();
    	}
    	throw new OrderBookException(OrderBookException.ORDER_BOOK_NOT_FOUND);
    }
	
	private OrderBook getOrderBookRef(String instrumentId) throws OrderBookException {
    	if(orderBookMap.containsKey(instrumentId)) {
    		return orderBookMap.get(instrumentId);
    	}
    	throw new OrderBookException(OrderBookException.ORDER_BOOK_NOT_FOUND);
    }
	
	
	 synchronized public void openOrderBook(String instrumentId) throws OrderBookException{
	        if(!orderBookMap.containsKey(instrumentId)){
	            OrderBook orderBook = new OrderBook(instrumentId);
	            orderBookMap.put(instrumentId, orderBook);
	           
	        }else{
	        	throw new OrderBookException(OrderBookException.ORDER_BOOK_ALREADY_OPENED);
	        }       
	 }
	 
	 synchronized public void closeOrderBook(String instrumentId) throws OrderBookException{       
	        OrderBook orderBook = getOrderBookRef(instrumentId);
	        if(orderBook.getStatus() == OrderBookStatus.OPEN){
	            orderBook.setStatus(OrderBookStatus.CLOSED);           
	        }else {
	        	throw new OrderBookException(OrderBookException.ORDER_BOOK_ALREADY_CLOSED);
	        }
	    }
	 
	 public void updateOrderBookDetails(OrderBook book) {
		 OrderBook dbOrderBook = orderBookMap.get(book.getInstrumentId());
		 dbOrderBook.setExecutionPrice(book.getExecutionPrice());
		 dbOrderBook.setExecutionStarted(book.isExecutionStarted());
		 dbOrderBook.setStatus(book.getStatus());
	 }
	
}
