package com.demotxt.myapp.recyclerview.ownmodels;

import com.google.gson.annotations.SerializedName;

public class StringResponceFromWeb {
    @SerializedName("strresult")
  private String  Strresult;
    public String getresult() {
        return Strresult;
    }
    public  void setStrresult(String rsl)
    {
        Strresult=rsl;
    }
}
