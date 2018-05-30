package com.example.coviam.myapp.Model.authentication;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class UserInfo {

        @SerializedName("userName")
        @Expose
        private String username;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("name")
        @Expose
        private String name;

        public String getUsername() {
            return username;
        }

        public UserInfo(String username, String password, String email, String address, String name) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.address = address;
            this.name = name;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

