package com.demotxt.myapp.recyclerview.CategoryFragments;

import com.demotxt.myapp.recyclerview.ownmodels.Prod;
import com.google.gson.annotations.SerializedName;

public class CatMen {
    @SerializedName("title")/// server side column name
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

    public CatMen(String title, String category, String description, String thumbnail, float price, int id) {
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
