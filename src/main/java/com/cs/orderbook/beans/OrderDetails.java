package com.cs.orderbook.beans;

public class OrderDetails {
	
	private Order order;
	private Long executionQuantity = 0L;
	private boolean isExecutionComplete;
	private OrderStatus status;
	
	public OrderDetails(Order order) {
		this.order = order;
	}
	public Order getOrder() {
		return order;
	}
	
	public Long getExecutionQuantity() {
		return executionQuantity;
	}
	public void setExecutionQuantity(Long executionQuantity) {
		this.executionQuantity = executionQuantity;
	}
	public boolean isExecutionComplete() {
		return isExecutionComplete;
	}
	public void setExecutionComplete(boolean isExecutionComplete) {
		this.isExecutionComplete = isExecutionComplete;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public long getRemainingQuantity() {
		return order.getQuantity() - executionQuantity;
	}
    
    
}
