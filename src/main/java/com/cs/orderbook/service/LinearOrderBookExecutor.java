package com.cs.orderbook.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.orderbook.beans.ExecutionRequest;
import com.cs.orderbook.beans.Order;
import com.cs.orderbook.dao.IOrderDAO;
import com.cs.orderbook.exceptions.OrderBookException;

@Component("linearExecutor")
public class LinearOrderBookExecutor implements IOrderBookExecutor {
	private static final Logger LOGGER = LoggerFactory.getLogger(LinearOrderBookExecutor.class);
	
	@Autowired
	IOrderDAO orderDAO;
	
	@Override
	public boolean execute(ExecutionRequest executionRequest) throws OrderBookException {
		long totalValidQuntity = orderDAO.getTotalValidUnExecutedOrderQuantity(executionRequest.getInstrumentId());
        LOGGER.info("total validQuantity:{}",totalValidQuntity);
        boolean executeOrderBook = true;
        long executionQuantity = executionRequest.getQuantity();
       
        List<Order> validOrderList = orderDAO.getValidUnExecutedOrders(executionRequest.getInstrumentId());
        LOGGER.info("total numValidOrders:{}", validOrderList.size());
        for(Order ord : validOrderList){
            if(!ord.isExecutionComplete()){
                float percentage = ord.getRemainingQuantity() / (float)totalValidQuntity;
                long quantity = Math.round(percentage * executionQuantity);
                totalValidQuntity = totalValidQuntity - ord.getRemainingQuantity();                             
                executionQuantity = executionQuantity - quantity + updateOrderExecutionQuantiy(ord, quantity);
                orderDAO.update(ord);
            }
            executeOrderBook &= ord.isExecutionComplete();
            if(executionQuantity <= 0) {
            	break;
            }
        }
        return executeOrderBook && validOrderList.size() > 0;
	}
	
	private long updateOrderExecutionQuantiy(Order order, long paramQuantity) {
		long diff = 0;
		long temp = order.getExecutionQuantity() + paramQuantity;		
		if(temp > order.getQuantity()){
			diff = temp - order.getQuantity();
			order.setExecutionQuantity(order.getQuantity());
		}else{
			order.setExecutionQuantity(temp);
		}
		order.setExecutionComplete(order.getExecutionQuantity() == order.getQuantity());
		return diff;
	}
}
