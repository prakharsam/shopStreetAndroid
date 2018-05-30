package com.example.coviam.myapp.Model.products;

    public class GetItemRequest {

        private Long cartid;
        private Long pid;
        private Long mid;
        private Long qty;
        private Double price;
        private String image;

        public GetItemRequest(Long cartid, Long pid, Long mid, Long qty, Double price, String image, String productName) {
            this.cartid = cartid;
            this.pid = pid;
            this.mid = mid;
            this.qty = qty;
            this.price = price;
            this.image = image;
            this.productName = productName;
        }

        private String productName;

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
