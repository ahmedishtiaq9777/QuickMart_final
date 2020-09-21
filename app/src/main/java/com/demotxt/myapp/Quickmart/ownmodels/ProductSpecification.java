package com.demotxt.myapp.Quickmart.ownmodels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductSpecification {

    @SerializedName("specificationId")
    @Expose
    private Integer specificationId;
    @SerializedName("productColor")
    @Expose
    private String productColor;
    @SerializedName("productSize")
    @Expose
    private String productSize;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("cart")
    @Expose
    private List<Object> cart = null;
    @SerializedName("cartProdescriptionPivot")
    @Expose
    private List<Object> cartProdescriptionPivot = null;
    @SerializedName("product")
    @Expose
    private Object product;

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Object getProduct() {
        return product;
    }

    public void setProduct(Object product) {
        this.product = product;
    }





}
