package com.cs.orderbook.beans;

public class OrderBook implements Cloneable{	

    String instrumentId;
    OrderBookStatus status;
    boolean executionStarted;
    double executionPrice;
    
    public OrderBook(String instrumentId){
        this.instrumentId = instrumentId;       
        status = OrderBookStatus.OPEN;       
    }

    public boolean isExecutionStarted() {
        return executionStarted;
    }

    public void setExecutionStarted(boolean executionStarted) {
        this.executionStarted = executionStarted;
    }  

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

	public OrderBookStatus getStatus() {
		return status;
	}

	public void setStatus(OrderBookStatus status) {
		this.status = status;
	}
	
	public void setExecutionPrice(double executionPrice) {
		this.executionPrice = executionPrice;
	}
	public double getExecutionPrice() {
		return executionPrice;
	}
	@Override
	public OrderBook clone() {
		try {
			return (OrderBook) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
