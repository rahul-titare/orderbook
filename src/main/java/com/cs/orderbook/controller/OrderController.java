package com.cs.orderbook.controller;


import java.util.Collections;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.orderbook.beans.AddOrderRequest;
import com.cs.orderbook.beans.ApiResponse;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.exceptions.OrderException;
import com.cs.orderbook.service.OrderBookService;

@RestController
@RequestMapping("/orderbook/order")
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderBookService bookService;


    @PostMapping("/add")
    public ApiResponse addOrder(@RequestBody @Valid AddOrderRequest request){
    	LOGGER.info("Add order request [{}]",request);
    	ApiResponse resp = new ApiResponse();
    	try {
			String orderId = bookService.addOrder(request);
			resp.success();
			resp.setData(Collections.singletonMap("orderId", orderId));
		} catch (OrderBookException e) {
			resp.error(e.getErrorCode());
		}
    	return resp;
    }
    
    @GetMapping("/get/{orderId}")
    public ApiResponse addOrder(@PathVariable("orderId") String orderId){
    	ApiResponse resp = new ApiResponse();
    	try {
    		resp.setData(bookService.fetchOrder(orderId));
			resp.success();			
		} catch (OrderBookException | OrderException e) {
			resp.error(e.getErrorCode());
		}
    	return resp;
    }


}
