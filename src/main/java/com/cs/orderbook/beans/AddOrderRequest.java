package com.cs.orderbook.beans;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

public class AddOrderRequest {
	@NotBlank
	String instrumentId;
	@Range(min = 1, max = 1000000)
    long quantity;
	@Range(min = 0, max = 1000000)
    double price;
    OrderType orderType;

    public AddOrderRequest(String instrumentId, long quantity, double price, OrderType orderType){
    	this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.price = price;
        this.orderType = orderType;
    }
    public String getInstrumentId() {
		return instrumentId;
	}
    public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    @Override
    public String toString() {    	
    	return new StringBuilder(200).append("instrumentId:").append(instrumentId).append(", quantity:").append(quantity).append(", price:").append(price).append(", ordertype:").append(orderType).toString();
    }
}
