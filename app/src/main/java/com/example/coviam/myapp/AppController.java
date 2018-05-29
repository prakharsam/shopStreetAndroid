package com.example.coviam.myapp;



import android.app.Application;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


    public class AppController extends Application {

        private static Retrofit retrofit = null;

        private static Retrofit retrofitForOrderHistory= null;
        public static AppController mInstance;
        OkHttpClient client;


        @Override
        public void onCreate() {
            super.onCreate();
            mInstance = this;
        }

        public static AppController getInstance() {
            return mInstance;
        }

        public Retrofit getClient() {
            if (null == retrofit) {
                client = new OkHttpClient.Builder().build();
                // Set the base url

                retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.177.2.31:4000/v1/cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }
            return retrofit;
        }

        public Retrofit getOrderHistoryClient() {
            if (null == retrofitForOrderHistory) {
                client = new OkHttpClient.Builder().build();
                // Set the base url

                retrofitForOrderHistory = new Retrofit.Builder()
                        .baseUrl("http://10.177.2.31:3000/v1/oms/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }
            return retrofitForOrderHistory;
        }

    }

