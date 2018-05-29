package com.example.coviam.myapp.Model;

import com.google.gson.annotations.SerializedName;

public class CheckoutRequestModel {

        @SerializedName("cartid")
        private Long cartid;
        @SerializedName("userid")
        private Long userid;
        @SerializedName("email")
        private String email;

        public CheckoutRequestModel(Long cartid, Long userid, String email) {
            this.cartid = cartid;
            this.userid = userid;
            this.email = email;
        }

        public Long getCartid() {
            return cartid;
        }

        public void setCartid(Long cartid) {
            this.cartid = cartid;
        }

        public Long getUserid() {
            return userid;
        }

        public void setUserid(Long userid) {
            this.userid = userid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

