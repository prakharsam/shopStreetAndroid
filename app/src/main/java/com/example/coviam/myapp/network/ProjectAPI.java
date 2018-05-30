package com.example.coviam.myapp.network;

import com.example.coviam.myapp.Model.cart.CartData;
import com.example.coviam.myapp.Model.cart.CartResponseDTO;
import com.example.coviam.myapp.Model.checkout.CheckoutRequestModel;
import com.example.coviam.myapp.Model.checkout.CheckoutResponseModel;
import com.example.coviam.myapp.Model.cart.DelRequestModel;
import com.example.coviam.myapp.Model.cart.DelResponseModel;
import com.example.coviam.myapp.Model.cart.GetCartResponse;
import com.example.coviam.myapp.Model.merchant.MerchantDto;
import com.example.coviam.myapp.Model.Orders.OrdersParentResponse;
import com.example.coviam.myapp.Model.authentication.ResponseFromUser;
import com.example.coviam.myapp.Model.authentication.UserInfo;
import com.example.coviam.myapp.Model.product.ProductDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectAPI {

    @POST("addUser")
    Call<ResponseFromUser> addUser(@Body UserInfo user);

    @GET("authorize")
    Call<ResponseFromUser> authorize(@Query("userName") String userName, @Query("password") String password);

    @GET("get-products-by-cid")
    Call<List<ProductDto>> getProductsByCid(@Query("categoryID") Long categoryID);

    @GET("get-product-by-id")
    Call<ProductDto> getProductById(@Query("productID") Long productID);

    @GET("get-merchants-by-pid")
    Call<List<MerchantDto>> getMerchantsByPid(@Query("productID") Long productID);

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
    Call<List<com.example.coviam.myapp.Model.products.ProductDto>>search(@Query("name")String name);
}
