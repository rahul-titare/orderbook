package com.cs.orderbook.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cs.orderbook.beans.AddOrderRequest;
import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.beans.OrderBook;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.beans.OrderType;
import com.cs.orderbook.beans.OrderbookStatistics;
import com.cs.orderbook.beans.StatsType;
import com.cs.orderbook.config.AppConfig;
import com.cs.orderbook.dao.OrderBookDAO;
import com.cs.orderbook.dao.OrderDAO;
import com.cs.orderbook.exceptions.ApiException;
import com.cs.orderbook.service.OrderBookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class OrderBookStatsTest {

	@Autowired
    OrderBookService bookService;
	
	@Autowired
	OrderBookDAO orderBookDAO;
	
	@Autowired
	OrderDAO orderDAO;
	
	@Test
    public void testStats() throws InterruptedException, ApiException {
    	String instrumentId = "ABCD";
        bookService.openOrderBook(instrumentId);     
        OrderBook book = bookService.getOrderBook(instrumentId);
        
   	 	bookService.addOrder(new AddOrderRequest(instrumentId, 10L, 45D, OrderType.LIMIT));   	 	
   	 	bookService.addOrder(new AddOrderRequest(instrumentId ,100L, 23D,OrderType.LIMIT));   	
		bookService.addOrder(new AddOrderRequest(instrumentId, 67L, 54D, OrderType.LIMIT));		
		bookService.addOrder(new AddOrderRequest(instrumentId, 25L, 50D, OrderType.LIMIT));		
		bookService.addOrder(new AddOrderRequest(instrumentId, 25L, 50D, OrderType.LIMIT));		
		bookService.closeOrderBook(instrumentId); 
		bookService.addExecution(new ExecutionRequest(instrumentId, 1,200)); 
		//bookService.addExecution(new ExecutionRequest(2,100), instrumentId);
		 
		OrderbookStatistics stats = bookService.collectStats(instrumentId, StatsType.ALL);
		
		//assertEquals(book.getOrderList().get(0).getOrderId(), stats.getFirstOrder().getOrderId());
		//assertEquals(book.getOrderList().get(4).getOrderId(), stats.getLastOrder().getOrderId());
		
		List<OrderDetails> allOrders = orderDAO.getAllOrders(instrumentId);
		
		assertEquals(allOrders.get(1).getOrder().getOrderId(), stats.getMaxOrder().getOrderId());
		assertEquals(allOrders.get(0).getOrder().getOrderId(), stats.getMinOrder().getOrderId());
       
	}
}
