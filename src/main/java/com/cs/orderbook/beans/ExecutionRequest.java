package com.cs.orderbook.beans;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

public class ExecutionRequest {
	@NotBlank
	private String instrumentId;
	@Range(min = 1, max = 1000000)
	private Long quantity;
	@Range(min = 0, max = 1000000)
	private Double price;

    public ExecutionRequest(String instrumentId, long quantity, double price){
    	this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.price = price;
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
    
    @Override
    public String toString() {    	
    	return new StringBuilder(200).append("instrumentId:").append(instrumentId).append(", quantity:").append(quantity).append(", price:").append(price).toString();
    }
}
