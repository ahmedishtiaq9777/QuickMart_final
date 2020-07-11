package com.demotxt.myapp.recyclerview.Order;

import com.google.gson.annotations.SerializedName;

public class Order_Detail {

    @SerializedName("title")
    private String Title;
    @SerializedName("price")
    private float Price;
    @SerializedName("quantity")
    private String Quantity;
    @SerializedName("productImage")
    private int Image;


    public Order_Detail(String Title, double Price, String Quantity, int Image) {
        this.Title = Title;
        this.Price = (float) Price;
        this.Quantity = Quantity;
        this.Image = Image;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return String.valueOf(Price);
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
