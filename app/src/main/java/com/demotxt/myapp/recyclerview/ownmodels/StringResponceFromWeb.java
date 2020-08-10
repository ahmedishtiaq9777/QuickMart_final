package com.demotxt.myapp.recyclerview.ownmodels;

import com.google.gson.annotations.SerializedName;

public class StringResponceFromWeb {
    @SerializedName("strresult")
  private String  Strresult;
    @SerializedName("userid")
    private int Userid;
    @SerializedName("error")
    private String ErrorResult;
    @SerializedName("username")
    private  String Username;
    @SerializedName("logo")
    private String Logo;

    public String getUsername() {
        return Username;
    }
    public  String getLogo(){
        return  Logo;
    }
    public  int getUserid(){
        return  Userid;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getErrorResult(){
        return  ErrorResult;
    }
    public  void setErrorResult(String errorResult)
    {
        ErrorResult=errorResult;
    }

    public String getresult() {
        return Strresult;
    }
    public  void setStrresult(String rsl)
    {
        Strresult=rsl;
    }
}
