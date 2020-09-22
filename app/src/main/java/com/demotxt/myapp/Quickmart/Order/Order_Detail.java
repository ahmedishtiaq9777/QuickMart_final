package com.demotxt.myapp.Quickmart.Order;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Order_Detail {

    @SerializedName("title")
    private String Title;
    @SerializedName("price")
    private float Price;
    @SerializedName("quantity")
    private String Quantity;
    @SerializedName("productImage")
    private String Image;



    public Order_Detail(String Title, double Price, String Quantity, String Image) {
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

    @NonNull
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
