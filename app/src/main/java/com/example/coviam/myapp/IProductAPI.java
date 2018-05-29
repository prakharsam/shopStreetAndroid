//package com.example.coviam.myapp;
//import com.example.coviam.myapp.Model.CartData;
//import com.example.coviam.myapp.Model.CartDto;
//import com.example.coviam.myapp.Model.CheckoutRequestModel;
//import com.example.coviam.myapp.Model.CheckoutResponseModel;
//import com.example.coviam.myapp.Model.DelRequestModel;
//import com.example.coviam.myapp.Model.DelResponseModel;
//import com.example.coviam.myapp.Model.GetItemRequest;
//import com.google.gson.annotations.SerializedName;
//
//import retrofit2.Call;
//import retrofit2.http.Body;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.Path;
//
//import java.util.List;
//
//public interface IProductAPI {
//    @GET("get/{id}")
//    Call<List<GetItemRequest>> getCart(@Path("id") Long id);
//    @POST("delete")
//    Call<DelResponseModel> deleteCart(@Body DelRequestModel delRequestModel );
//    @POST("add")
//    Call<CartData>addToCartApi(@Body CartData cartData);
//    @POST("checkout")
//    Call<CheckoutResponseModel> checkout(@Body CheckoutRequestModel checkoutRequestModel);
//}