package com.demotxt.myapp.recyclerview.CategoryFragments;

import com.demotxt.myapp.recyclerview.ownmodels.Prod;

public class CatMen {

    private String Title;
    private String Category;
    private String Description;
    private String Thumbnail;
    private Double Price;
    private  int Id;

    public CatMen(String title, String category, String description, String thumbnail, double price, int id) {
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
    public  String getPrice(){return String.valueOf(Price);}


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
