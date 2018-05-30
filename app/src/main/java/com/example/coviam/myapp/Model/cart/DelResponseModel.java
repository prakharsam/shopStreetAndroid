package com.example.coviam.myapp.Model.cart;



    import com.google.gson.annotations.SerializedName;

    public class DelResponseModel {
        @SerializedName("success")
        private Boolean success;
        @SerializedName("reason")
        private String reason;

        public DelResponseModel(Boolean success, String reason) {
            this.success = success;
            this.reason = reason;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

