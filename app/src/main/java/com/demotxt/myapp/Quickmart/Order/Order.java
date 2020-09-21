package com.demotxt.myapp.Quickmart.Order;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orderId")
    private String OrderId;
    @SerializedName("date")
    private String OrderDate;
    @SerializedName("status")
    private String OrderStatus;
    @SerializedName("total")
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

    public String getPrice(){return Price;}

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
