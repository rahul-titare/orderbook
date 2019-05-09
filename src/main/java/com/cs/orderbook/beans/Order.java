package com.cs.orderbook.beans;


import java.time.LocalDateTime;

public final class Order{
	private String orderId;
	private Long quantity;
	private LocalDateTime entryDate;
	private String instrumentId;
	private Double price;
	private OrderType orderType;    

    public Order(String orderId,String instrumentId, long quantity, double price, OrderType orderType){
    	this.orderId = orderId;
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;        
        entryDate = LocalDateTime.now();
    }

    
    public String getOrderId() {
		return orderId;
	}
    public Long getQuantity() {
        return quantity;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public Double getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }  
}
