package com.example.coviam.myapp.Model;

import com.google.gson.annotations.SerializedName;

public class CartDto {



        @SerializedName("cartId")
        private Long cartid;
        @SerializedName("pid")
        private Long pid;
        @SerializedName("mid")
        private Long mid;

        private Long qty;
        @SerializedName("price")
        private Double price;
        @SerializedName("image")
        private String image;
        @SerializedName("productName")
        private String productName;

        public CartDto(Long cartid, Long pid, Long mid, Long qty, Double price, String image, String productName) {
            this.cartid = cartid;
            this.pid = pid;
            this.mid = mid;
            this.qty = qty;
            this.price = price;
            this.image = image;
            this.productName = productName;
        }

        public Long getCartid() {
            return cartid;
        }

        public void setCartid(Long cartid) {
            this.cartid = cartid;
        }

        public Long getPid() {
            return pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public Long getMid() {
            return mid;
        }

        public void setMid(Long mid) {
            this.mid = mid;
        }

        public Long getQty() {
            return qty;
        }

        public void setQty(Long qty) {
            this.qty = qty;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }
    }

