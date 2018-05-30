package com.example.coviam.myapp.network;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginController extends Application {


    private static Retrofit retrofit = null;
    private static Retrofit retrofitProduct=null;
    private static Retrofit retrofitcart=null;
    private static Retrofit retrofitsearch=null;
    private static Retrofit retrofitForOrderHistory= null;
    public static LoginController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static LoginController getInstance() {

        return mInstance;
    }

    public Retrofit getLoginClient() {
        if (null == retrofit) {
            OkHttpClient client = new OkHttpClient.Builder().build();
            // Set the base url

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.177.1.106:8080/user/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }

    public Retrofit getProductClient() {
        if (null == retrofitProduct) {
            OkHttpClient client = new OkHttpClient.Builder().build();


            retrofitProduct = new Retrofit.Builder()
                    .baseUrl("http://10.177.1.131:8080/product/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitProduct;
    }


    public Retrofit getClient() {
        if (null == retrofitcart) {
            OkHttpClient client = new OkHttpClient.Builder().build();

            retrofitcart = new Retrofit.Builder()
                    .baseUrl("http://10.177.2.31:4000/v1/cart/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitcart;
    }

    public Retrofit getOrderHistoryClient() {
        if (null == retrofitForOrderHistory) {
            OkHttpClient client = new OkHttpClient.Builder().build();


            retrofitForOrderHistory = new Retrofit.Builder()
                    .baseUrl("http://10.177.2.31:3000/v1/oms/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitForOrderHistory;
    }

    public Retrofit getSearchClient() {
        if (null == retrofitsearch) {
            OkHttpClient client = new OkHttpClient.Builder().build();


            retrofitsearch = new Retrofit.Builder()
                    .baseUrl("http://10.177.1.106:8090/")
                   .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofitsearch;
    }

}

