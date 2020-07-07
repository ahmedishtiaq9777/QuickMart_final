package com.demotxt.myapp.recyclerview.Order;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("")
    private String OrderId;
    @SerializedName("")
    private String OrderDate;
    @SerializedName("")
    private String OrderStatus;
    @SerializedName("")
    private String Price;


    public Order(String OrderId, String OrderDate, String OrderStatus,String Price) {
        this.OrderId = OrderId;
        this.OrderDate = OrderDate;
        this.OrderStatus = OrderStatus;
        this.Price = Price;
    }


    public String getOrderId() {
        return OrderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public String getPrice(){return Price;};

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public void setPrice(String price){Price = price;}

}
