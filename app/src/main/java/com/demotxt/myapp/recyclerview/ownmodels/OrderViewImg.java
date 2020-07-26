package com.demotxt.myapp.recyclerview.ownmodels;

public class OrderViewImg {
    public String image;
    public String title;
    public int quantity;
    public double price;

    public OrderViewImg() {
    }

    public OrderViewImg(String image, String title, int quantity, double price) {
        this.image = image;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }
    public String getImage(){
        return image;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getTitle(){
        return title;
    }
    public double getPrice(){
        return price;
    }

}
