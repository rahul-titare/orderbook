package com.cs.orderbook.beans;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderbookStatistics {

	public static class OrderDetails{
		String orderId;
	    long quantity;
	    LocalDateTime entryDate;	    
	    double price;
	    OrderType orderType;
		public OrderDetails(String orderId, long quantity, LocalDateTime entryDate, double price, OrderType orderType) {
			super();
			this.orderId = orderId;
			this.quantity = quantity;
			this.entryDate = entryDate;
			this.price = price;
			this.orderType = orderType;
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
		public double getPrice() {
			return price;
		}
		public OrderType getOrderType() {
			return orderType;
		}
		
		
	}
	
	public static class DemandPrice{
		double price;
		long demand;
		public DemandPrice(double price, long demand) {
			super();
			this.price = price;
			this.demand = demand;
		}
		public double getPrice() {
			return price;
		}
		public long getDemand() {
			return demand;
		}
		
	}
	
	String instrumentId;
	long numOrders;
	long demand;
	OrderDetails maxOrder;
	OrderDetails minOrder;
	OrderDetails firstOrder;
	OrderDetails lastOrder;
	List<DemandPrice> limitBreakDown;
	
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
	public OrderDetails getMaxOrder() {
		return maxOrder;
	}
	public void setMaxOrder(Order order) {
		if(order!=null) {
			this.maxOrder = new OrderDetails(order.getOrderId(), order.getQuantity(), order.getEntryDate(), order.getPrice(), order.getOrderType());
		}
	}
	public OrderDetails getMinOrder() {
		return minOrder;
	}
	public void setMinOrder(Order order) {
		if(order!=null) {
			this.minOrder = new OrderDetails(order.getOrderId(), order.getQuantity(), order.getEntryDate(), order.getPrice(), order.getOrderType());
		}
	}
	public OrderDetails getFirstOrder() {
		return firstOrder;
	}
	public void setFirstOrder(Order order) {
		if(order!=null) {
			this.firstOrder = new OrderDetails(order.getOrderId(), order.getQuantity(), order.getEntryDate(), order.getPrice(), order.getOrderType());
		}
	}
	public OrderDetails getLastOrder() {
		return lastOrder;
	}
	public void setLastOrder(Order order) {
		if(order!=null) {
			this.lastOrder = new OrderDetails(order.getOrderId(), order.getQuantity(), order.getEntryDate(), order.getPrice(), order.getOrderType());
		}
	}
	
	public List<DemandPrice> getLimitBreakDown() {
		return limitBreakDown;
	}
	
	
}
