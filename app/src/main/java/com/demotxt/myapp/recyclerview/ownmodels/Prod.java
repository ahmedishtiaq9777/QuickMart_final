package com.demotxt.myapp.recyclerview.ownmodels;

import android.media.Rating;

import com.google.gson.annotations.SerializedName;

public class Prod {
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
    private int Id;
    @SerializedName("avgRating")
    private float Rating;

    public Prod(String title, String category, String description, String thumbnail,float price,int id,double rating) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Price=price;
         Id=id;
         Rating = (float) rating;
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

    public  void setPrice(float price){Price=Price;}
}
