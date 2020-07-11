package com.demotxt.myapp.recyclerview.ownmodels;

import com.google.gson.annotations.SerializedName;

public class StringResponceFromWeb {
    @SerializedName("strresult")
  private String  Strresult;
    @SerializedName("error")
    private String ErrorResult;
    @SerializedName("username")
    private  String Username;

    public String getUsername() {
        return Username;
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
