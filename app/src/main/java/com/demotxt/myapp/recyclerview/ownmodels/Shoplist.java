package com.demotxt.myapp.recyclerview.ownmodels;

public class Shoplist {
    private int mImage;
    private String mtitle, mEmail;

    public Shoplist(int image, String Title, String Mail) {
        mImage = image;
        mtitle = Title;
        mEmail = Mail;
    }

    public int getImage() {
        return mImage;
    }

    public String gettitle() {
        return mtitle;
    }

    public String getEmail() {
        return mEmail;
    }




}
