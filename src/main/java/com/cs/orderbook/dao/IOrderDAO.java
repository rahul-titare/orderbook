package com.cs.orderbook.dao;

import java.util.List;

import com.cs.orderbook.beans.Order;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.exceptions.OrderException;

public interface IOrderDAO {	
	public void addOrder(Order order) throws OrderBookException;
    public OrderDetails fetchOrder(String orderId) throws OrderBookException, OrderException;    
    public void updateOrderValidity(String instrumentId, double price) throws OrderBookException;    
    public long getTotalValidUnExecutedOrderQuantity(String instrumentId) throws OrderBookException;    
    public List<Order> getValidUnExecutedOrders(String instrumentId) throws OrderBookException;    
    public void update(Order order);    
    public List<Order> getAllValidOrders(String instrumentId) throws OrderBookException;
    public List<Order> getAllInValidOrders(String instrumentId) throws OrderBookException;
    public List<Order> getAllOrders(String instrumentId) throws OrderBookException;    
}
