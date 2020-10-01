package com.demotxt.myapp.Quickmart.ownmodels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserViewLog  implements Serializable {
@SerializedName("userud")
    String userid;
@SerializedName("category")
    String category;
@SerializedName("NOV")
    String NOV;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNOV() {
        return NOV;
    }

    public void setNOV(String NOV) {
        this.NOV = NOV;
    }
}
