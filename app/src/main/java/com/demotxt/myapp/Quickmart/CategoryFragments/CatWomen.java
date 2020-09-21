package com.demotxt.myapp.Quickmart.CategoryFragments;

import com.google.gson.annotations.SerializedName;

public class CatWomen {
    @SerializedName("title")// Server side  column name
    private String Title;
    private String Category;
    @SerializedName("description")
    private String Description;
    @SerializedName("productImage")
    private String Thumbnail;
    @SerializedName("price")
    private final float Price;
    @SerializedName("productId")
    private  int Id;

    @SerializedName("avgRating")
    private float Rating;
    @SerializedName("userId")
    private final int UserId;
    public CatWomen(String title, String category, String description, String thumbnail, float price, int id,double rating,int userId) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Price= price;
        Id=id;
        Rating = (float) rating;
        UserId=userId;

    }


    public String getTitle() {
        return Title;
    }
    public int getId(){return  Id;}

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }
    public  float getPrice(){return Price;}
    public float getRating() {
        return  Rating;
    }

    public int getUserId() {
        return UserId;
    }

    public void setRating(float rating) {
        Rating = rating;
    }


    public void setTitle(String title) {
        Title = title;
    }
    public  void setId(int id){Id=id;}

    public void setCategory(String category) {
        Category = category;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

}
