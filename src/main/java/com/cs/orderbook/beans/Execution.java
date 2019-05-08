package com.cs.orderbook.beans;

public class Execution {

    long quantity;
    double price;

    public Execution(long quantity, double price){
        this.quantity = quantity;
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
