package com.demotxt.myapp.recyclerview.Order;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("")
    private String OrderId;
    @SerializedName("")
    private String OrderDate;
    @SerializedName("")
    private String OrderDetail;
    @SerializedName("")
    private String Price;


    public Order(String OrderId, String OrderDate, String OrderDetail,String Price) {
        this.OrderId = OrderId;
        this.OrderDate = OrderDate;
        this.OrderDetail = OrderDetail;
        this.Price = Price;
    }


    public String getOrderId() {
        return OrderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public String getOrderDetail() {
        return OrderDetail;
    }

    public String getPrice(){return Price;};

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public void setOrderDetail(String orderDetail) {
        OrderDetail = orderDetail;
    }

    public void setPrice(String price){Price = price;}

}
