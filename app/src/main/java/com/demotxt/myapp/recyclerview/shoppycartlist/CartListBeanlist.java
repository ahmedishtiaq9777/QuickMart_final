package com.demotxt.myapp.recyclerview.shoppycartlist;

/**
 * Created by apple on 15/03/16.
 */
public class CartListBeanlist {

    private int image;
    private String title;

    private String price;

    public CartListBeanlist(int image, String title, String price) {
        this.image = image;
        this.title = title;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
