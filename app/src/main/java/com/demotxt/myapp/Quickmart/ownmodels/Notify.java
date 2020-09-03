package com.demotxt.myapp.Quickmart.ownmodels;

import com.google.gson.annotations.SerializedName;

public class Notify {
    private String Text;


    public Notify(String text) {
       Text = text;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
