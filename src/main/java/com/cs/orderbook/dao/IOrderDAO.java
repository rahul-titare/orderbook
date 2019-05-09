package com.cs.orderbook.dao;

import java.util.List;
import java.util.function.Consumer;

import com.cs.orderbook.beans.AddOrderRequest;
import com.cs.orderbook.beans.Order;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.exceptions.OrderException;

public interface IOrderDAO {	
	public Order addOrder(AddOrderRequest order) throws OrderBookException;
    public OrderDetails fetchOrder(String orderId) throws OrderBookException, OrderException;    
    public void updateOrderValidity(String instrumentId, Consumer<OrderDetails> validator) throws OrderBookException;    
    public long getTotalValidUnExecutedOrderQuantity(String instrumentId) throws OrderBookException;   
    public List<OrderDetails> getAllValidOrders(String instrumentId) throws OrderBookException;
    public List<OrderDetails> getAllInValidOrders(String instrumentId) throws OrderBookException;
    public List<OrderDetails> getAllOrders(String instrumentId) throws OrderBookException;
	public void applyExecution(String instrumentId, Consumer<OrderDetails> applier);    
}
