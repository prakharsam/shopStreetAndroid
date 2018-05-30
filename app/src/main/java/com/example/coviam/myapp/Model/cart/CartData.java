package com.example.coviam.myapp.Model.cart;
import com.google.gson.annotations.SerializedName;

public class CartData {
        @SerializedName("cartID")
        Long cartid;
        @SerializedName("productId")
        Long pid;
        @SerializedName("qty")
        Long qty;
        @SerializedName("merchantId")
        Long mid;
        @SerializedName("productPrice")
        double price;


        public CartData(Long cartid, Long productId, Long qty, Long merchantId, double price) {
            this.cartid = cartid;
            this.pid = productId;
            this.qty = qty;
            this.mid = merchantId;
            this.price = price;
        }

        public Long getCartid() {
            return cartid;
        }

        public void setCartid(Long cartid) {
            this.cartid = cartid;
        }

        public Long getProductId() {
            return pid;
        }

        public void setProductId(Long productId) {
            this.pid = productId;
        }

        public Long getQty() {
            return qty;
        }

        public void setQty(Long qty) {
            this.qty = qty;
        }

        public Long getMid() {
            return mid;
        }

        public void setMid(Long mid) {
            this.mid = mid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }


