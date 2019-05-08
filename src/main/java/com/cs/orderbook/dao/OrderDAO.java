package com.cs.orderbook.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cs.orderbook.beans.Order;
import com.cs.orderbook.beans.OrderBook;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.beans.OrderStatus;
import com.cs.orderbook.beans.OrderType;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.exceptions.OrderException;

@Repository("inMemory")
public class OrderDAO implements IOrderDAO {
	private List<Order> orderList = new ArrayList<>();
	private Map<String, Order> orderMap = new HashMap<>();
	
	@Autowired
	OrderBookDAO orderBookDAO;
	
	synchronized public void addOrder(Order order) throws OrderBookException{	        
	        orderList.add(order);
	        String generatedOrderId = UUID.randomUUID().toString();
	        order.setOrderId(generatedOrderId);
	        orderMap.put(generatedOrderId, order);
	    }
	 
	    public OrderDetails fetchOrder(String orderId) throws OrderBookException, OrderException{
	    	Order order = orderMap.get(orderId);	        	       
	        if(order == null) {
	        	throw new OrderException(OrderException.ORDER_NOT_FOUND);
	        }
	        OrderBook orderBook = orderBookDAO.getOrderBook(order.getInstrumentId());
	        return new OrderDetails(order.getQuantity(), order.getExecutionQuantity(), order.getPrice(), orderBook.getExecutionPrice(), order.getStatus(),order.getOrderType());
	    }
	    
	    public void updateOrderValidity(String instrumentId, double price) throws OrderBookException {
	    	orderList.stream().filter(ord->ord.getInstrumentId().equals(instrumentId)).forEach(ord->{
	    		if(ord.getOrderType() == OrderType.LIMIT && ord.getPrice() < price) {
					ord.setStatus(OrderStatus.INVALID);
					ord.setExecutionComplete(true);			
				}else {
					ord.setStatus(OrderStatus.VALID);
				}
	    	});	    	
	    }
	    
	    public long getTotalValidUnExecutedOrderQuantity(String instrumentId) throws OrderBookException {	    	
	    	return orderList.stream().filter(ord->ord.getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.VALID && !ord.isExecutionComplete()).mapToLong(o->o.getRemainingQuantity()).sum();
	    }
	    
	    public List<Order> getValidUnExecutedOrders(String instrumentId) throws OrderBookException{
	    	return orderList.stream().filter(ord->ord.getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.VALID && !ord.isExecutionComplete()).map((o)->o.clone()).collect(Collectors.toList());	    	
	    }	    
	        
	    public List<Order> getAllValidOrders(String instrumentId) throws OrderBookException{	    	
	    	return orderList.stream().filter(ord ->ord.getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.VALID).map((o)->o.clone()).collect(Collectors.toList());
	    }
	    public List<Order> getAllInValidOrders(String instrumentId) throws OrderBookException{	    	
	    	return orderList.stream().filter(ord -> ord.getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.INVALID).map((o)->o.clone()).collect(Collectors.toList());
	    }
	    public List<Order> getAllOrders(String instrumentId) throws OrderBookException{
	    	return orderList.stream().filter(ord -> ord.getInstrumentId().equals(instrumentId)).map((o)->o.clone()).collect(Collectors.toList());
	    }

		@Override
		public void update(Order order) {
			Order dbOrder = orderMap.get(order.getOrderId());
	    	dbOrder.setExecutionComplete(order.isExecutionComplete());
	    	dbOrder.setExecutionQuantity(order.getExecutionQuantity());			
		}
}
