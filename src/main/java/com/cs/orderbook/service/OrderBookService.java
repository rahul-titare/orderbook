package com.cs.orderbook.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cs.orderbook.beans.AddOrderRequest;
import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.beans.Order;
import com.cs.orderbook.beans.OrderBook;
import com.cs.orderbook.beans.OrderBookStatus;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.beans.OrderType;
import com.cs.orderbook.beans.OrderbookStatistics;
import com.cs.orderbook.beans.StatsType;
import com.cs.orderbook.dao.IOrderDAO;
import com.cs.orderbook.dao.OrderBookDAO;
import com.cs.orderbook.exceptions.ApiException;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.exceptions.OrderException;




@Service
public class OrderBookService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderBookService.class);
	@Autowired
	OrderBookDAO orderBookDAO;
	
	@Autowired
	@Qualifier("inMemory")
	IOrderDAO orderDAO;
	
	@Autowired
	@Qualifier("linearExecutor")
	IOrderBookExecutor executor;
    
    public OrderBook getOrderBook(String instrumentId) throws OrderBookException {
    	return orderBookDAO.getOrderBook(instrumentId);
    }

    public void openOrderBook(String instrumentId) throws OrderBookException{
    	LOGGER.info("opening orderbook for instrumentId:{}", instrumentId);
    	orderBookDAO.openOrderBook(instrumentId);
    }


    public void closeOrderBook(String instrumentId) throws OrderBookException{
    	LOGGER.info("closing orderbook for instrumentId:{}", instrumentId);
    	orderBookDAO.closeOrderBook(instrumentId);
    }

    public String addOrder(AddOrderRequest addRequest) throws OrderBookException{
    	LOGGER.info("adding order in orderbook for instrumentId:{}", addRequest.getInstrumentId());
        OrderBook orderBook = orderBookDAO.getOrderBook(addRequest.getInstrumentId());
        if(orderBook.getStatus() == OrderBookStatus.OPEN){
            Order order = new Order(addRequest.getInstrumentId(), addRequest.getQuantity(),addRequest.getOrderType() == OrderType.MARKET?0:addRequest.getPrice(), addRequest.getOrderType());
            orderDAO.addOrder(order);
            LOGGER.info("order:{} added in orderbook for instrumentId:{}", order.getOrderId(), addRequest.getInstrumentId());
            return order.getOrderId();
        }else {
        	throw new OrderBookException(OrderBookException.ORDER_BOOK_NOT_OPEN);
        }       
    }
    
    public OrderDetails fetchOrder(String orderId) throws OrderBookException, OrderException{        
        return orderDAO.fetchOrder(orderId);
    }



    synchronized public boolean addExecution(ExecutionRequest executionRequest) throws ApiException{
    	LOGGER.info("adding execution in orderbook for instrumentId:{}", executionRequest.getInstrumentId());
        OrderBook orderBook = orderBookDAO.getOrderBook(executionRequest.getInstrumentId());
        if(orderBook.getStatus() == OrderBookStatus.CLOSED){
        	if(!orderBook.isExecutionStarted()) {
    			orderBook.setExecutionStarted(true);
    			orderBook.setExecutionPrice(executionRequest.getPrice());
    			orderDAO.updateOrderValidity(executionRequest.getInstrumentId(), executionRequest.getPrice());
    		}    		    	
        	boolean isOrderBookExecuted = executor.execute(executionRequest);
            if(isOrderBookExecuted){
            	orderBook.setStatus(OrderBookStatus.EXECUTED);
            	LOGGER.info("orderbook instrumentId:{} executed", executionRequest.getInstrumentId());
            }
            orderBookDAO.updateOrderBookDetails(orderBook);
            return isOrderBookExecuted;           	
        }else if(orderBook.getStatus() == OrderBookStatus.OPEN){
        	throw new OrderBookException(OrderBookException.ORDER_BOOK_NOT_CLOSED);
        }else if(orderBook.getStatus() == OrderBookStatus.EXECUTED){
        	throw new OrderBookException(OrderBookException.ORDER_BOOK_EXECUTED);
        }else {
        	throw new ApiException();
        }
    }
    

    
    public OrderbookStatistics collectStats(String instrumentId, StatsType statsType) throws OrderBookException{
    	LOGGER.info("collecting stats for instrumentId:{} statsType:{}", instrumentId, statsType);
    	OrderBook book = orderBookDAO.getOrderBook(instrumentId);    	
    	List<Order> resultList = null;
    	if(statsType == StatsType.VALID) {
    		resultList = orderDAO.getAllValidOrders(instrumentId);
    	}else if(statsType == StatsType.INVALID) {
    		resultList = orderDAO.getAllInValidOrders(instrumentId);
    	}else {
    		resultList = orderDAO.getAllOrders(instrumentId);
    	}
    	OrderbookStatistics stats = new OrderbookStatistics();
    	stats.setInstrumentId(book.getInstrumentId());    	    	
    	long numOrders = resultList.size();
    	Order ord = numOrders>0?resultList.get(0):null;
    	Order minOrder = ord;
    	Order maxOrder = ord;
    	Order firstOrder = ord;
    	Order lastOrder = ord;
    	long demand = 0;
    	Map<Double, Long> priceDemand = new HashMap<>();
    	for(Order order : resultList) {    		
    		if(order.getQuantity() < minOrder.getQuantity()) {
    			minOrder = order;
    		}
    		if(order.getQuantity() > maxOrder.getQuantity()) {
    			maxOrder = order;
    		}
    		if(order.getEntryDate().isBefore(firstOrder.getEntryDate())) {
    			firstOrder = order;
    		}
    		if(order.getEntryDate().isAfter(lastOrder.getEntryDate())) {
    			lastOrder = order;
    		}
    		demand += order.getQuantity();
    		priceDemand.compute(order.getPrice(), (k, v)->v == null? order.getQuantity() : v + order.getQuantity());    		
    	}	
		stats.setNumOrders(numOrders);
		stats.setDemand(demand);
		stats.setMaxOrder(maxOrder);
		stats.setMinOrder(minOrder);
		stats.setFirstOrder(firstOrder);
		stats.setLastOrder(lastOrder);
		for(Entry<Double, Long> entry : priceDemand.entrySet()) {
			stats.addDemandPrice(entry.getKey(), entry.getValue());
		}
				
		return stats;
    }
}
