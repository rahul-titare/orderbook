package com.cs.orderbook.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cs.orderbook.beans.AddOrderRequest;
import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.beans.OrderBook;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.beans.OrderType;
import com.cs.orderbook.config.AppConfig;
import com.cs.orderbook.dao.OrderBookDAO;
import com.cs.orderbook.dao.OrderDAO;
import com.cs.orderbook.exceptions.ApiException;
import com.cs.orderbook.service.OrderBookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class LinearQuantityTest {
	@Autowired
    OrderBookService bookService;
	
	@Autowired
	OrderBookDAO orderBookDAO;
	
	@Autowired
	OrderDAO orderDAO;

	//@Test
    public void testQuantity1() throws ApiException {
		String instrumentId = "ABC";
		bookService.openOrderBook(instrumentId);
		OrderBook book = bookService.getOrderBook(instrumentId);
		
		bookService.addOrder(new AddOrderRequest(instrumentId, 7L, 34D, OrderType.LIMIT));
		bookService.addOrder(new AddOrderRequest(instrumentId, 17L, 34D,OrderType.LIMIT)); 
		bookService.addOrder(new AddOrderRequest(instrumentId, 18L, 34D, OrderType.LIMIT));
		bookService.closeOrderBook(instrumentId);
		bookService.addExecution(new ExecutionRequest(instrumentId,10,100));
		 
		
		List<OrderDetails> allOrders = orderDAO.getAllOrders(instrumentId);
		
		assertEquals(allOrders.get(0).getExecutionQuantity().longValue(), 2);
		assertEquals(allOrders.get(1).getExecutionQuantity().longValue(), 4);
		assertEquals(allOrders.get(2).getExecutionQuantity().longValue(), 4);	 
		
		bookService.addExecution(new ExecutionRequest(instrumentId,1,100));
		
		assertEquals(allOrders.get(0).getExecutionQuantity().longValue(), 2);
		assertEquals(allOrders.get(1).getExecutionQuantity().longValue(), 4);
		assertEquals(allOrders.get(2).getExecutionQuantity().longValue(), 5);
	}
	
	
	//@Test
    public void testQuantity2() throws ApiException {
    	String instrumentId = "ABCD";
        bookService.openOrderBook(instrumentId);     
        OrderBook book = bookService.getOrderBook(instrumentId);
        
   	 	bookService.addOrder(new AddOrderRequest(instrumentId, 1L, 34D, OrderType.LIMIT)); 
   	 	bookService.addOrder(new AddOrderRequest(instrumentId ,1L, 34D,OrderType.LIMIT)); 
		bookService.addOrder(new AddOrderRequest(instrumentId, 1L, 34D, OrderType.LIMIT));
		bookService.addOrder(new AddOrderRequest(instrumentId, 1L, 34D, OrderType.LIMIT));
		bookService.addOrder(new AddOrderRequest(instrumentId, 1L, 34D, OrderType.LIMIT));		
		bookService.closeOrderBook(instrumentId); 
		bookService.addExecution(new ExecutionRequest(instrumentId, 1,100)); 
		//bookService.addExecution(new ExecutionRequest(2,100), instrumentId);
		 
		List<OrderDetails> allOrders = orderDAO.getAllOrders(instrumentId);
       
        
        assertEquals(allOrders.get(0).getExecutionQuantity().longValue(), 0);
        assertEquals(allOrders.get(1).getExecutionQuantity().longValue(), 0);
        assertEquals(allOrders.get(2).getExecutionQuantity().longValue(), 0);
        assertEquals(allOrders.get(3).getExecutionQuantity().longValue(), 1);
        assertEquals(allOrders.get(4).getExecutionQuantity().longValue(), 0);
        
       
	}
	
	
}
