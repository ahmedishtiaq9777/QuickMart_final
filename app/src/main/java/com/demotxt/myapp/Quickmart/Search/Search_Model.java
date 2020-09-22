package com.demotxt.myapp.Quickmart.Search;

import com.google.gson.annotations.SerializedName;

public class Search_Model {
    @SerializedName("title")
    private String Title;
    private String Category;
    @SerializedName("description")
    private String Description;
    @SerializedName("productImage")
    private String Thumbnail;
    @SerializedName("price")
    private float Price;
    @SerializedName("productId")
    private final int Id;
    @SerializedName("avgRating")
    private float Rating;
    @SerializedName("userId")
    private int Sid;

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public Search_Model(String title, String category, String description, String thumbnail, float price, int id, double rating, int sid) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Price=price;
         Id=id;
         Rating = (float) rating;
         Sid = sid;
    }


    public String getTitle() {
        return Title;
    }

    public int getId(){return Id;}

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public float getPrice(){return  Price;}

    public float getRating() {
        return  Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public  void setPrice(float price){Price=price;}
}
