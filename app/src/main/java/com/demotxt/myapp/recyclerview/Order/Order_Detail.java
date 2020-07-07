package com.demotxt.myapp.recyclerview.Order;

import com.google.gson.annotations.SerializedName;

public class Order_Detail {

    @SerializedName("")
    private String Title;
    @SerializedName("")
    private String Price;
    @SerializedName("")
    private String Quantity;
    @SerializedName("")
    private int Image;


    public Order_Detail(String Title, String Price, String Quantity, int Image) {
        this.Title = Title;
        this.Price = Price;
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
        return Price;
    }

    public void setPrice(String price) {
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
