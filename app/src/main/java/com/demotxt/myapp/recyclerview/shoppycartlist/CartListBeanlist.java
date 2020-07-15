package com.demotxt.myapp.recyclerview.shoppycartlist;

import com.google.gson.annotations.SerializedName;

public class CartListBeanlist {
    @SerializedName("productImage")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private float price;
    @SerializedName("productId")
    private  int Id;
    @SerializedName("description")
    private String desc;




    public CartListBeanlist(String image, String title, float price, int id, String desc) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.Id=id;
        this.desc = desc;
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

    public float getPrice() {
        return price;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
