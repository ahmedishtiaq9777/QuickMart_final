package com.demotxt.myapp.Quickmart.Cart_Fav;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CartListBeanlist implements Serializable {
    @SerializedName("productImage")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private double price;
    @SerializedName("productId")
    private  int Id;
    @SerializedName("userQuantity")
    private int Quantity;
    @SerializedName("sellerQuantity")
    private int sellerQuantity;
    @SerializedName("sellerId")
    private  int Sellerid;



    public CartListBeanlist(String image, String title, double price,int id,int quantity,int  sellerQuantity,int sellerid) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.Id=id;
        this.Quantity=quantity;
        this.sellerQuantity=sellerQuantity;
        this.Sellerid=sellerid;
    }

    public int getSellerid() {
        return Sellerid;
    }



    public int getSellerQuantity() {
        return sellerQuantity;
    }

    public void setSellerQuantity(int sellerQuantity) {
        this.sellerQuantity = sellerQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }
    public int getId(){return Id;}

    public void setTitle(String title) {
        this.title = title;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQuantity() {
        return Quantity;
    }


    public void setQuantity(int quantity) {
        Quantity = quantity;
    }


}
