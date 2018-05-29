package com.example.coviam.myapp.Model.Orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrdersItem{

	@SerializedName("amount")
	private double amount;

	@SerializedName("id")
	private int id;

	@SerializedName("items")
	private List<ItemsItem> items;

	public void setAmount(double amount){
		this.amount = amount;
	}

	public double getAmount(){
		return amount;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"OrdersItem{" + 
			"amount = '" + amount + '\'' + 
			",id = '" + id + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}