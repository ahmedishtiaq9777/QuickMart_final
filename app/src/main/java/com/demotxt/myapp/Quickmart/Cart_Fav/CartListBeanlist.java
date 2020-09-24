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
    @SerializedName("description")
    private String Description;
    @SerializedName("specificationid")
    private int Specificationid;
    @SerializedName("size")
    private String Size;
    @SerializedName("color")
    private String Color;



    public CartListBeanlist(String image, String title, double price,int id,int quantity,int  sellerQuantity,int sellerid, String description,int specificationid, String size, String color) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.Id=id;
        this.Quantity=quantity;
        this.sellerQuantity=sellerQuantity;
        this.Sellerid=sellerid;
        this.Description = description;
        this.Specificationid=specificationid;
        this.Size=size;
        this.Color=color;

    }
    public CartListBeanlist(){}

    public int getSellerid() {
        return Sellerid;
    }

    public int getSpecificationid() {
        return Specificationid;
    }

    public void setSpecificationid(int specificationid) {
        Specificationid = specificationid;
    }

    public void setSellerid(int sellerid) {
        Sellerid = sellerid;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getColor() { return Color; }

    public void setColor(String color) {
        Color = color;
    }



}
