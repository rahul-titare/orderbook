package com.cs.orderbook.beans;


import java.time.LocalDateTime;

public class Order implements Cloneable{
	String orderId;
    long quantity;
    LocalDateTime entryDate;
    String instrumentId;
    double price;
    OrderType orderType;    
    long executionQuantity;
    boolean isExecutionComplete;
    OrderStatus status;

    public Order(String instrumentId, long quantity, double price, OrderType orderType){    	
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;        
        entryDate = LocalDateTime.now();
    }

    public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    public String getOrderId() {
		return orderId;
	}
    public long getQuantity() {
        return quantity;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public double getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }   

    public long getExecutionQuantity() {
        return executionQuantity;
    }
    
    public void setExecutionQuantity(long executionQuantity) {
		this.executionQuantity = executionQuantity;
	}

       
    public long getRemainingQuantity() {
    	return quantity - executionQuantity;
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
	
	@Override
	public Order clone(){		
		try {
			return (Order) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
