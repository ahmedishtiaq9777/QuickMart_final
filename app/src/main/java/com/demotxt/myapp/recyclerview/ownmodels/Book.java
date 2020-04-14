package com.demotxt.myapp.recyclerview.ownmodels;

public class Book {

    private String Title;
    private String Thumbnail;


    public Book(String title, String thumbnail) {
        Title = title;
        Thumbnail = thumbnail;

    }

    public String getTitle() {
        return Title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

}
