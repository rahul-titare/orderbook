package com.cs.orderbook.beans;


import java.util.ArrayList;
import java.util.List;

public class OrderbookStatistics {	
	
	private static class DemandPrice{
		Double price;
		Long demand;
		public DemandPrice(double price, long demand) {
			super();
			this.price = price;
			this.demand = demand;
		}
		public Double getPrice() {
			return price;
		}
		public Long getDemand() {
			return demand;
		}
		
	}
	
	private String instrumentId;
	private long numOrders;
	private long demand;
	private Order maxOrder;
	private Order minOrder;
	private Order firstOrder;
	private Order lastOrder;
	private List<DemandPrice> limitBreakDown;
	
	public OrderbookStatistics() {
		limitBreakDown = new ArrayList<>();
	}
	
	public void addDemandPrice(double price, long demand) {
		limitBreakDown.add(new DemandPrice(price, demand));
	}
	
	public String getInstrumentId() {
		return instrumentId;
	}
	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}
	public long getNumOrders() {
		return numOrders;
	}
	public void setNumOrders(long numOrders) {
		this.numOrders = numOrders;
	}
	public long getDemand() {
		return demand;
	}
	public void setDemand(long demand) {
		this.demand = demand;
	}
	public Order getMaxOrder() {
		return maxOrder;
	}
	public void setMaxOrder(Order order) {		
		this.maxOrder = order;		
	}
	public Order getMinOrder() {
		return minOrder;
	}
	public void setMinOrder(Order order) {
		this.minOrder = order;		
	}
	public Order getFirstOrder() {
		return firstOrder;
	}
	public void setFirstOrder(Order order) {
		this.firstOrder = order;		
	}
	public Order getLastOrder() {
		return lastOrder;
	}
	public void setLastOrder(Order order) {
		this.lastOrder = order;	
	}
	
	public List<DemandPrice> getLimitBreakDown() {
		return limitBreakDown;
	}
	
	
}
