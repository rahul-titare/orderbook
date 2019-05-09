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
import com.cs.orderbook.beans.OrderBookStatus;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.beans.OrderStatus;
import com.cs.orderbook.beans.OrderType;
import com.cs.orderbook.config.AppConfig;
import com.cs.orderbook.dao.OrderBookDAO;
import com.cs.orderbook.dao.OrderDAO;
import com.cs.orderbook.exceptions.ApiException;
import com.cs.orderbook.service.OrderBookService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class OrderBookServiceTest {

    @Autowired
    OrderBookService bookService;
    
    @Autowired
	OrderBookDAO orderBookDAO;
	
	@Autowired
	OrderDAO orderDAO;

    @Test
    public void testBook() throws ApiException{
    	String instrumentId = "ABC";
        bookService.openOrderBook(instrumentId);
        OrderBook book = bookService.getOrderBook(instrumentId);
        bookService.addOrder(new AddOrderRequest(instrumentId, 90L, 34D, OrderType.LIMIT));
        bookService.addOrder(new AddOrderRequest(instrumentId, 10L, 135D, OrderType.LIMIT));
        bookService.closeOrderBook(instrumentId);
        bookService.addExecution(new ExecutionRequest(instrumentId, 90,100));       
        
        List<OrderDetails> allOrders = orderDAO.getAllOrders(instrumentId);
        assertEquals(allOrders.get(0).getStatus(), OrderStatus.VALID);
        assertEquals(allOrders.get(0).getStatus(), OrderStatus.INVALID);
        
        assertEquals(book.getStatus(), OrderBookStatus.EXECUTED);

    }  
   
}
