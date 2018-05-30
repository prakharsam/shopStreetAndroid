package com.example.coviam.myapp.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.CartAdapter;
import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.Model.checkout.CheckoutRequestModel;
import com.example.coviam.myapp.Model.checkout.CheckoutResponseModel;
import com.example.coviam.myapp.Model.cart.DelRequestModel;
import com.example.coviam.myapp.Model.cart.DelResponseModel;
import com.example.coviam.myapp.Model.cart.GetCartResponse;
import com.example.coviam.myapp.Model.products.GetItemRequest;
import com.example.coviam.myapp.network.ProjectAPI;
import com.example.coviam.myapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPageActivity extends AppCompatActivity implements CartAdapter.IAdapterCommunicator {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<GetItemRequest> productList;
    private ProjectAPI mIProductApi1;
    private Toolbar mTopToolbar;
    private TextView noDataFound;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_recycler_view);
        alertDialog= new AlertDialog.Builder(CartPageActivity.this).create();
        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        productList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler2_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIProductApi1 = LoginController.getInstance().getClient().create(ProjectAPI.class);
        noDataFound = findViewById(R.id.tv_no_data_found);

        cartAdapter = new CartAdapter(CartPageActivity.this, productList, this);
        recyclerView.setAdapter(cartAdapter);
        callViewCartApi();


        Button checkout = findViewById(R.id.check_out);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
                Long userId = preferences.getLong("id", 0L);
                String email = preferences.getString("email", "DEFAULT");
                if (email.equals("DEFAULT"))

                {
                    Intent intent = new Intent(CartPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                CheckoutRequestModel checkoutRequestModel = new CheckoutRequestModel(userId, userId, email);
                final ProgressDialog progressDialog = new ProgressDialog(CartPageActivity.this);
                progressDialog.show();
                Call<CheckoutResponseModel> call = mIProductApi1.checkout(checkoutRequestModel);
                call.enqueue(new Callback<CheckoutResponseModel>() {
                    @Override
                    public void onResponse(Call<CheckoutResponseModel> call, Response<CheckoutResponseModel> response)

                    {
                        if (null != response.body()) {
                            if (response.code() == 200 && response.body().getSuccess()) {
                                productList.clear();

                                cartAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                                //Read Update
                                alertDialog.setTitle("Congrats!!");
                                alertDialog.setMessage("Your Order Is Placed." + response.body().getOrderid());


                                alertDialog.show();
                            } else {
                                        noDataFound.setVisibility(View.VISIBLE);

                            }
                        } else {
                            //Toast.makeText(CartPageActivity.this,"response is empty in cart",Toast.LENGTH_LONG).show();
                            alertDialog.setTitle("OOps!!");
                            alertDialog.setMessage("your order could'nt be placed");
                            alertDialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckoutResponseModel> call, Throwable t) {
                        progressDialog.dismiss();

                        alertDialog.setTitle("OOps!!");
                        alertDialog.setMessage("Something Went wrong with our Server .Try  again!!");
                        alertDialog.show();

                    }
                });
            }

        });

    }

    private void callViewCartApi() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        Long userid = preferences.getLong("id", 0);

        Call<GetCartResponse> call = mIProductApi1.getCart(userid);

        call.enqueue(new Callback<GetCartResponse>() {
            @Override
            public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {

                if (null != response.body()) {

                    if (response.body().isSuccess() && !response.body().getItems().isEmpty()) {
                        productList.clear();
                        productList.addAll(response.body().getItems());
                        cartAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                    else{
                        progressDialog.dismiss();
                        alertDialog.setTitle("Oooh!!");
                        alertDialog.setMessage("Your Cart Is Empty");
                        alertDialog.show();
                    }
                } else {
                    noDataFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                progressDialog.dismiss();
                alertDialog.setTitle("OOps!!");
                alertDialog.setMessage("Something went wrong .Try again!!");
                alertDialog.show();
            }

        });


        progressDialog.show();

    }

    @Override
    public void delete(final int position, Long cartId, Long prId, Long merchantId) {

        DelRequestModel delRequestModel = new DelRequestModel(cartId, prId, merchantId);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<DelResponseModel> call = mIProductApi1.deleteCart(delRequestModel);
        call.enqueue(new Callback<DelResponseModel>() {
            @Override
            public void onResponse(Call<DelResponseModel> call, Response<DelResponseModel> response) {

                if (null != response.body()) {
                    if (response.code() == 200 && response.body().getSuccess()) {
                        productList.remove(position);
                        cartAdapter.notifyItemRemoved(position);
                        progressDialog.dismiss();
                    } else {
                        alertDialog.setTitle("OOps!!");
                        alertDialog.setMessage("your order could'nt be deleted");
                        alertDialog.show();
                    }
                } else {
                    noDataFound.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<DelResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                alertDialog.setTitle("OOps!!");
                alertDialog.setMessage("Something Went Wrong .Try Again!!");
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuresource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_home) {
            Intent displayByCategory = new Intent(CartPageActivity.this, DisplayByCategoryActivity.class);
            startActivity(displayByCategory);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}