package com.demotxt.myapp.recyclerview.ownmodels;

import com.google.gson.annotations.SerializedName;

public class Book {
   @SerializedName("shopName")
    private String Title;
    @SerializedName("logo")
    private String Thumbnail;
    @SerializedName("userId")
    private int id;
    @SerializedName("d_kilometers")
    private String Distance;


    public Book(String title, String thumbnail,int uid,float dist) {
        Title = title;
        Thumbnail = thumbnail;
        id=uid;
        Distance = String.valueOf(dist);

    }

    public String getTitle() {
        return Title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public int getUserId(){return id;}

    public void setId(int uid){  id=uid;}

    public void setTitle(String title) {
        Title = title;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
       Distance = distance;
    }

}
