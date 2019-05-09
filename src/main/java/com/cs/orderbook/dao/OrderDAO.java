package com.cs.orderbook.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cs.orderbook.beans.AddOrderRequest;
import com.cs.orderbook.beans.Order;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.beans.OrderStatus;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.exceptions.OrderException;

@Repository("inMemory")
public class OrderDAO implements IOrderDAO {
	private List<OrderDetails> orderList = new ArrayList<>();
	private Map<String, OrderDetails> orderMap = new HashMap<>();
	
	@Autowired
	OrderBookDAO orderBookDAO;
	
	synchronized public Order addOrder(AddOrderRequest orderRequest) throws OrderBookException{
			Order order = new Order(UUID.randomUUID().toString(), orderRequest.getInstrumentId(), orderRequest.getQuantity(), orderRequest.getPrice(), orderRequest.getOrderType());
			OrderDetails details = new OrderDetails(order);
	        orderList.add(details);	        
	        orderMap.put(order.getOrderId(), details);
	        return order;
	    }
	 
	    public OrderDetails fetchOrder(String orderId) throws OrderBookException, OrderException{
	    	return orderMap.get(orderId);	                
	    }
	    
	    public void updateOrderValidity(String instrumentId, Consumer<OrderDetails> validator) throws OrderBookException {
	    	orderList.stream().filter(ord->ord.getOrder().getInstrumentId().equals(instrumentId)).forEach(validator);	    	
	    }
	    
	    public long getTotalValidUnExecutedOrderQuantity(String instrumentId) throws OrderBookException {	    	
	    	return orderList.stream().filter(ordDetails->ordDetails.getOrder().getInstrumentId().equals(instrumentId) && ordDetails.getStatus() == OrderStatus.VALID && !ordDetails.isExecutionComplete()).mapToLong(o->o.getRemainingQuantity()).sum();
	    }       
	        
	    public List<OrderDetails> getAllValidOrders(String instrumentId) throws OrderBookException{	    	
	    	return orderList.stream().filter(ord ->ord.getOrder().getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.VALID).collect(Collectors.toList());
	    }
	    public List<OrderDetails> getAllInValidOrders(String instrumentId) throws OrderBookException{	    	
	    	return orderList.stream().filter(ord -> ord.getOrder().getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.INVALID).collect(Collectors.toList());
	    }
	    public List<OrderDetails> getAllOrders(String instrumentId) throws OrderBookException{
	    	return orderList.stream().filter(ord -> ord.getOrder().getInstrumentId().equals(instrumentId)).collect(Collectors.toList());
	    }

		@Override
		public void applyExecution(String instrumentId, Consumer<OrderDetails> applier) {
			orderList.stream().filter(ord->ord.getOrder().getInstrumentId().equals(instrumentId) && ord.getStatus() == OrderStatus.VALID && !ord.isExecutionComplete()).forEach(applier);
		}
	}
