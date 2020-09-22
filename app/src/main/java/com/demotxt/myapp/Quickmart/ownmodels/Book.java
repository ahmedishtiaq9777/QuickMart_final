package com.demotxt.myapp.Quickmart.ownmodels;

import android.content.Context;

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
    @SerializedName("rating")
    private float Rating;
    @SerializedName("contact")
    private String Contact;
    @SerializedName("address")
    private String Address;

    public Book() {
    }

    public Book(String title, String thumbnail, int uid, float dist, double rating, String contact, String address) {
        Title = title;
        Thumbnail = thumbnail;
        id=uid;
        Distance = String.valueOf(dist);
        Rating = (float) rating;
        Contact = contact;
        Address = address;
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

    public float getRating() {
        return  Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

}
