package com.example.coviam.myapp.Model.cart;

public class CartModel {
    private int img_id;
    private String prName;
    private String mrName;
    private int quantity;
    private double price;

    public CartModel(int img_id, String prName, String mrName, int quantity, double price) {
        this.img_id = img_id;
        this.prName = prName;
        this.mrName = mrName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }

    public String getPrName() {
        return prName;
    }

    public void setPrName(String prName) {
        this.prName = prName;
    }

    public String getMrName() {
        return mrName;
    }

    public void setMrName(String mrName) {
        this.mrName = mrName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

