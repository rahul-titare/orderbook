package com.cs.orderbook.beans;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

public class AddOrderRequest {
	@NotBlank
	private String instrumentId;
	@Range(min = 1, max = 1000000)
	private Long quantity;
	@Range(min = 0, max = 1000000)
	private Double price;
    OrderType orderType;

    public AddOrderRequest(String instrumentId, Long quantity, Double price, OrderType orderType){
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
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
