package com.cs.orderbook.beans;

public class OrderBook{	

	private String instrumentId;
	private OrderBookStatus status;
	private boolean executionStarted;
	private Double executionPrice;
    
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
	
	public void setExecutionPrice(Double executionPrice) {
		this.executionPrice = executionPrice;
	}
	public Double getExecutionPrice() {
		return executionPrice;
	}

}
