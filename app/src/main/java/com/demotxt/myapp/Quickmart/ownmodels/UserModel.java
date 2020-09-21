package com.demotxt.myapp.Quickmart.ownmodels;

public class UserModel {
    String Phone;
    String Address;
    String UserName;
    String Image;

    public UserModel(){

    }

    public UserModel(String phone, String address, String userName, String image) {
        this.Phone = phone;
        this.Address = address;
        this.UserName = userName;
        this.Image = image;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }



}
