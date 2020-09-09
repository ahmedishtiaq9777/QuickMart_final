package com.demotxt.myapp.Quickmart.ownmodels;

public class DetailModel {

    public DetailModel(String name, String email, String phonenumber, String address) {
        this.name = name;
        this.email = email;
        this.phone = phonenumber;
        this.address = address;
    }

    String name;
    String phone;
    String email;
    String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

