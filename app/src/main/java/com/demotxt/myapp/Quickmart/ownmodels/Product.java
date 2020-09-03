
package com.demotxt.myapp.Quickmart.ownmodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("cart")
    @Expose
    private List<Object> cart = null;
    @SerializedName("cartProdescriptionPivot")
    @Expose
    private List<Object> cartProdescriptionPivot = null;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Object> getCart() {
        return cart;
    }

    public void setCart(List<Object> cart) {
        this.cart = cart;
    }

    public List<Object> getCartProdescriptionPivot() {
        return cartProdescriptionPivot;
    }

    public void setCartProdescriptionPivot(List<Object> cartProdescriptionPivot) {
        this.cartProdescriptionPivot = cartProdescriptionPivot;
    }


}
