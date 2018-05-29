package com.example.coviam.myapp.Model.Orders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OrdersParentResponse{

	@SerializedName("orders")
	private List<OrdersItem> orders;

	@SerializedName("userId")
	private int userId;

	public void setOrders(List<OrdersItem> orders){
		this.orders = orders;
	}

	public List<OrdersItem> getOrders(){
		return orders;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	@Override
 	public String toString(){
		return 
			"OrdersParentResponse{" + 
			"orders = '" + orders + '\'' + 
			",userId = '" + userId + '\'' + 
			"}";
		}
}