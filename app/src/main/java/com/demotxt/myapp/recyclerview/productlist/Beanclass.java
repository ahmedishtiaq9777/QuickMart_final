package com.demotxt.myapp.recyclerview.productlist;

/**
 * Created by Rp on 3/30/2016.
 */


//********GRIDVIEW************
public class Beanclass {
    private String image;
    private String title;
    private String price;
    private String cutprice;
    private String discount;
    private String ratingtext;


    public Beanclass(String image, String title, String price, String cutprice, String discount, String ratingtext) {

        this.image = image;
        this.title = title;
        this.price = price;
        this.cutprice = cutprice;
        this.discount = discount;
        this.ratingtext = ratingtext;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCutprice() {
        return cutprice;
    }

    public void setCutprice(String cutprice) {
        this.cutprice = cutprice;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    public String getRatingtext() {
        return ratingtext;
    }

    public void setRatingtext(String ratingtext) {
        this.ratingtext = ratingtext;
    }





}
