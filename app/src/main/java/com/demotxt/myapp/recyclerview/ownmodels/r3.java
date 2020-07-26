package com.demotxt.myapp.recyclerview.ownmodels;

import com.google.gson.annotations.SerializedName;

public class r3 {

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
    private  int Id;

    public r3() {
    }

    public r3(String title, String category, String description, String thumbnail,float price,int id) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Price=price;
        Id=id;
    }


    public String getTitle() {
        return Title;
    }
    public  int getId(){return  Id;}

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getThumbnail() {
        return Thumbnail;
    }
    public  String  getPrice(){return  String.valueOf(Price);}


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
}
