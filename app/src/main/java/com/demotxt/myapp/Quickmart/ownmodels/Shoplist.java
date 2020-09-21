package com.demotxt.myapp.Quickmart.ownmodels;

public class Shoplist {
    private final int mImage;
    private final String mtitle;
    private final String mEmail;

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
