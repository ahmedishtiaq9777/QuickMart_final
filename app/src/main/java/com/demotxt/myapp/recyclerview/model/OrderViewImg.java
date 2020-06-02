package com.demotxt.myapp.recyclerview.model;

public class OrderViewImg {
    public int image;
    public String title;
    public int quantity;
    public int price;

    public OrderViewImg() {
    }

    public OrderViewImg(int image, String title, int quantity, int price) {
        this.image = image;
        this.title = title;
        this.quantity = quantity;
        this.price = price;
    }
    public int getImage(){
        return image;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getTitle(){
        return title;
    }
    public int getPrice(){
        return price;
    }

}
