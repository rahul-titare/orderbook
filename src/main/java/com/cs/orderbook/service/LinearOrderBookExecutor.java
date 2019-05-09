package com.cs.orderbook.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.beans.OrderDetails;
import com.cs.orderbook.dao.IOrderDAO;
import com.cs.orderbook.exceptions.OrderBookException;

@Component("linearExecutor")
public class LinearOrderBookExecutor implements IOrderBookExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinearOrderBookExecutor.class);
	
	@Autowired
	IOrderDAO orderDAO;
	
	@Override
	public boolean execute(ExecutionRequest executionRequest) throws OrderBookException {       
        boolean[] executeOrderBook = {true};
        long totalValidQuntityOut = orderDAO.getTotalValidUnExecutedOrderQuantity(executionRequest.getInstrumentId());
        LOGGER.info("total valid unexecuted order quantity:{}",totalValidQuntityOut);
        Consumer<OrderDetails> quantityApplier = new Consumer<OrderDetails>() {
			
        	long totalValidQuntity = totalValidQuntityOut;
        	long executionQuantity = executionRequest.getQuantity();  
        	
			@Override
			public void accept(OrderDetails details) {
				if(executionQuantity > 0 && !details.isExecutionComplete()){
	                float percentage = details.getRemainingQuantity() / (float)totalValidQuntity;
	                long quantity = Math.round(percentage * executionQuantity);
	                totalValidQuntity = totalValidQuntity - details.getRemainingQuantity();                             
	                executionQuantity = executionQuantity - quantity + updateOrderExecutionQuantiy(details, quantity);
	                executeOrderBook[0] &= details.isExecutionComplete();
	            }	            		
			}
		};
		
		orderDAO.applyExecution(executionRequest.getInstrumentId(), quantityApplier);		
        return executeOrderBook[0] && totalValidQuntityOut>0;
	}
	
	private long updateOrderExecutionQuantiy(OrderDetails details, long paramQuantity) {
		long diff = 0;
		long temp = details.getExecutionQuantity() + paramQuantity;		
		if(temp > details.getOrder().getQuantity()){
			diff = temp - details.getOrder().getQuantity();
			details.setExecutionQuantity(details.getOrder().getQuantity());
		}else{
			details.setExecutionQuantity(temp);
		}
		details.setExecutionComplete(details.getExecutionQuantity().equals(details.getOrder().getQuantity()));
		return diff;
	}
}
