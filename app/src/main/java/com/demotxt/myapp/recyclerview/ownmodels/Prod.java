package com.demotxt.myapp.recyclerview.ownmodels;

public class Prod {

    private String Title;
    private String Category;
    private String Description;
    private String Thumbnail;
    private float Price;
    private int Id;

    public Prod() {
    }

    public Prod(String title, String category, String description, String thumbnail,double price,int id) {
        Title = title;
        Category = category;
        Description = description;
        Thumbnail = thumbnail;
        Price=(float)price;
         Id=id;
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
