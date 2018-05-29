package com.example.coviam.myapp.Model.Orders;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{


	@SerializedName("image")
	private String image;

	@SerializedName("productName")
	private String productName;

	@SerializedName("price")
	private double price;

	@SerializedName("qty")
	private int qty;

	@SerializedName("mid")
	private int mid;

	@SerializedName("pid")
	private int pid;

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setQty(int qty){
		this.qty = qty;
	}

	public int getQty(){
		return qty;
	}

	public void setMid(int mid){
		this.mid = mid;
	}

	public int getMid(){
		return mid;
	}

	public void setPid(int pid){
		this.pid = pid;
	}

	public int getPid(){
		return pid;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"price = '" + price + '\'' + 
			",qty = '" + qty + '\'' + 
			",mid = '" + mid + '\'' + 
			",pid = '" + pid + '\'' + 
			"}";
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