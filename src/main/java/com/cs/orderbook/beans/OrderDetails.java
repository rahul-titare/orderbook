package com.cs.orderbook.beans;

public class OrderDetails {

	
	long quantity;
	long executionQuantity;
	double price;
	double executionPrice;
	OrderStatus status;
	OrderType type;
	
	public OrderDetails(long quantity, long executionQuantity, double price, double executionPrice, OrderStatus orderStatus, OrderType type) {		
		this.status = orderStatus;
		this.quantity = quantity;
		this.executionQuantity = executionQuantity;
		this.price = price;
		this.executionPrice = executionPrice;
		this.type = type;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public long getQuantity() {
		return quantity;
	}
	public double getPrice() {
		return price;
	}
	public double getExecutionPrice() {
		return executionPrice;
	}
	public long getExecutionQuantity() {
		return executionQuantity;
	}
	
	public OrderType getType() {
		return type;
	}
	
}
