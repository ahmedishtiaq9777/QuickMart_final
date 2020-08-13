package com.demotxt.myapp.recyclerview.ownmodels;

public class ShippingModel {

    public ShippingModel(String name, String email, String contact, String address, String code, String city) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.code = code;
        this.city = city;
    }

    String name;
    String email;
    String contact;
    String address;
    String code;
    String  city;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }




}
