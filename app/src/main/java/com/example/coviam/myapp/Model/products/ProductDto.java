package com.example.coviam.myapp.Model.products;

public class ProductDto {


    private long productID;
    private String productName;
    private String productImgUrl;
    private Double productPrice;
    private long merchantID;
    private String productMerchantName;
    private int merchantCount;

    @Override
    public String toString() {
        return "ProductDto{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productImgUrl='" + productImgUrl + '\'' +
                ", productPrice=" + productPrice +
                ", merchantID=" + merchantID +
                ", productMerchantName='" + productMerchantName + '\'' +
                ", merchantCount=" + merchantCount +
                '}';
    }

    public ProductDto() {
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public long getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(long merchantID) {
        this.merchantID = merchantID;
    }

    public String getProductMerchantName() {
        return productMerchantName;
    }

    public void setProductMerchantName(String productMerchantName) {
        this.productMerchantName = productMerchantName;
    }

    public int getMerchantCount() {
        return merchantCount;
    }

    public void setMerchantCount(int merchantCount) {
        this.merchantCount = merchantCount;
    }
}

