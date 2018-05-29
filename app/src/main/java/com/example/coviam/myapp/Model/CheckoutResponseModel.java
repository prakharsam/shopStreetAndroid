package com.example.coviam.myapp.Model;


    import com.google.gson.annotations.SerializedName;

    public class CheckoutResponseModel {
        @SerializedName("success")
        private Boolean  success;
        @SerializedName("orderid")
        private Long orderid;
        @SerializedName("reason")
        private String reason;


        public CheckoutResponseModel(Boolean success, Long orderid, String reason) {
            this.success = success;
            this.orderid = orderid;
            this.reason = reason;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Long getOrderid() {
            return orderid;
        }

        public void setOrderid(Long orderid) {
            this.orderid = orderid;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

