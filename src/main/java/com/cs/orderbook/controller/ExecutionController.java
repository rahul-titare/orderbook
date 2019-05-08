package com.cs.orderbook.controller;



import java.util.Collections;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cs.orderbook.beans.ApiResponse;
import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.exceptions.ApiException;
import com.cs.orderbook.service.OrderBookService;

@RestController
@RequestMapping("/orderbook")
public class ExecutionController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionController.class);

    @Autowired
    OrderBookService bookService;

    @PostMapping("/execute")
    public ApiResponse execute(@RequestBody @Valid ExecutionRequest executionRequest){
    	LOGGER.info("Add execution request [{}]",executionRequest);
    	ApiResponse resp = new ApiResponse();
        try {
			boolean isClosed =  bookService.addExecution(executionRequest);
			resp.success();
			resp.setData(Collections.singletonMap("orderBookedExecuted", isClosed));
		} catch (ApiException e) {
			resp.error(e.getErrorCode());
		}
        return resp;
    }

}
