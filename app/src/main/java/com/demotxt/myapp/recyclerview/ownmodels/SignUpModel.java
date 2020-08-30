package com.demotxt.myapp.recyclerview.ownmodels;

public class SignUpModel {

    public SignUpModel(String name, String phonenumber, String password) {
        this.name = name;
        this.phone = phonenumber;
        this.password = password;
    }

    String name;
    String phone;
    String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
