package com.demotxt.myapp.recyclerview.shoppycartlist;

/**
 * Created by apple on 15/03/16.
 */
public class CartListBeanlist {

    private String image;
    private String title;

    private double price;

    public CartListBeanlist(String image, String title, double price) {
        this.image = image;
        this.title = title;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
