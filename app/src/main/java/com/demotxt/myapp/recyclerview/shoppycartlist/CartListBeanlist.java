package com.demotxt.myapp.recyclerview.shoppycartlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 15/03/16.
 */
public class CartListBeanlist {
    @SerializedName("productImage")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private double price;
    @SerializedName("productId")
    private  int Id;



    public CartListBeanlist(String image, String title, double price,int id) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.Id=id;
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
}
