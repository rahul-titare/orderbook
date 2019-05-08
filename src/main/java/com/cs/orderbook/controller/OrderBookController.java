package com.cs.orderbook.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.orderbook.beans.ApiResponse;
import com.cs.orderbook.beans.StatsType;
import com.cs.orderbook.exceptions.OrderBookException;
import com.cs.orderbook.service.OrderBookService;

@RestController
@RequestMapping("/orderbook")
public class OrderBookController {

    @Autowired
    OrderBookService bookService;

    @GetMapping("/open/{instrumentId}")
    public ApiResponse openOrderBook(@PathVariable("instrumentId") String instrumentId){
    	ApiResponse resp = new ApiResponse();
        try {
			bookService.openOrderBook(instrumentId);
			resp.success();
		} catch (OrderBookException e) {
			resp.error(e.getErrorCode());
		}
        return resp;       
    }

    @GetMapping("/close/{instrumentId}")
    public ApiResponse closeOrderBook(@PathVariable("instrumentId") String instrumentId){
    	ApiResponse resp = new ApiResponse();
		try {
			bookService.closeOrderBook(instrumentId);
			resp.success();
		} catch (OrderBookException e) {
			resp.error(e.getErrorCode());			
		}			
		return resp;   
    }
    
    @GetMapping("stats/{instrumentId}/{statsType}")
    public ApiResponse collectStats(@PathVariable("instrumentId") String instrumentId, @PathVariable("statsType") StatsType statsType) {
    	ApiResponse resp = new ApiResponse();
    	try {
			resp.setData(bookService.collectStats(instrumentId, statsType));
			resp.success();
		} catch (OrderBookException e) {
			resp.error(e.getErrorCode());
		}
    	return resp;
    }
}
