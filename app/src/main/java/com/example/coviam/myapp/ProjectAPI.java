package com.example.coviam.myapp;

import com.example.coviam.myapp.Model.CartData;
import com.example.coviam.myapp.Model.CartResponseDTO;
import com.example.coviam.myapp.Model.CheckoutRequestModel;
import com.example.coviam.myapp.Model.CheckoutResponseModel;
import com.example.coviam.myapp.Model.DelRequestModel;
import com.example.coviam.myapp.Model.DelResponseModel;
import com.example.coviam.myapp.Model.GetCartResponse;
import com.example.coviam.myapp.Model.MerchantDto;
import com.example.coviam.myapp.Model.Orders.OrdersParentResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectAPI {

    @POST("addUser")
    Call<ResponseFromUser> addUser(@Body User user);
    @GET("authorize")
    Call<ResponseFromUser> authorize(@Query("userName") String userName, @Query("password") String password);
    @GET("get-products-by-cid")
    Call<List<ProductDto>> getproductsbycid(@Query("categoryID") Long categoryID);
    @GET("get-product-by-id")
    Call<ProductDto>getproductbyid(@Query("productID") Long productID);
    @GET("get-merchants-by-pid")
    Call<List<MerchantDto>> getmerchantsbypid(@Query("productID") Long productID);
    @GET("get/{id}")
    Call<GetCartResponse> getCart(@Path("id") Long id);
    @POST("delete")
    Call<DelResponseModel> deleteCart(@Body DelRequestModel delRequestModel );
    @POST("add")
    Call<CartResponseDTO>addToCartApi(@Body CartData cartData);
    @POST("checkout")
    Call<CheckoutResponseModel> checkout(@Body CheckoutRequestModel checkoutRequestModel);
    @GET("order/history/{userid}")
    Call<OrdersParentResponse> orderHistory(@Path("userid") Long id);
    @GET("search")
    Call<List<com.example.coviam.myapp.Model.ProductDto>>search(@Query("name")String name);
}
