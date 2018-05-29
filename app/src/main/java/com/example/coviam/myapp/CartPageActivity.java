package com.example.coviam.myapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.CartAdapter;
import com.example.coviam.myapp.Model.CheckoutRequestModel;
import com.example.coviam.myapp.Model.CheckoutResponseModel;
import com.example.coviam.myapp.Model.DelRequestModel;
import com.example.coviam.myapp.Model.DelResponseModel;
import com.example.coviam.myapp.Model.GetCartResponse;
import com.example.coviam.myapp.Model.GetItemRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartPageActivity extends AppCompatActivity implements CartAdapter.IAdapterCommunicator {
    RecyclerView recyclerview;
    CartAdapter cartAdapter;
    List<GetItemRequest> productlist;
    ProjectAPI mIProductAPI1;
    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_recycler_view);
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        productlist = new ArrayList<>();
        recyclerview = findViewById(R.id.recycler2_view);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mIProductAPI1 = LoginController.getInstance().getClient().create(ProjectAPI.class);


        cartAdapter = new CartAdapter(CartPageActivity.this, productlist, this);
        recyclerview.setAdapter(cartAdapter);
        callViewCartApi();


        Button checkout = findViewById(R.id.check_out);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
                Long userId = preferences.getLong("id", 0L);
                String email = preferences.getString("email", "DEFAULT");
                if (email == "DEFAULT")

                {
                    Intent intent = new Intent(CartPageActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                CheckoutRequestModel checkoutRequestModel = new CheckoutRequestModel(userId,userId, email);
                final ProgressDialog progressDialog = new ProgressDialog(CartPageActivity.this);
                progressDialog.show();
                Call<CheckoutResponseModel> call = mIProductAPI1.checkout(checkoutRequestModel);
                call.enqueue(new Callback<CheckoutResponseModel>() {
                    @Override
                    public void onResponse(Call<CheckoutResponseModel> call, Response<CheckoutResponseModel> response) {
                        if (response.code() == 200 && response.body().getSuccess()) {
                            productlist.clear();

                            cartAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(CartPageActivity.this).create(); //Read Update
                            alertDialog.setTitle("Congrats!!");
                            alertDialog.setMessage("your order is placed." + response.body().getOrderid());


                            alertDialog.show();
                        } else {
                            Toast.makeText(CartPageActivity.this, response.body().getReason() + "", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckoutResponseModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(CartPageActivity.this, "Failed to Delete", Toast.LENGTH_LONG);
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

        Call<GetCartResponse> call = mIProductAPI1.getCart(userid);

        call.enqueue(new Callback<GetCartResponse>() {
            @Override
            public void onResponse(Call<GetCartResponse> call, Response<GetCartResponse> response) {

                if (response.body().isSuccess()) {
                    System.out.println("dhjcgasdjg");
                    Toast.makeText(CartPageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    productlist.clear();
                    productlist.addAll(response.body().getItems());
                    cartAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    //Toast.makeText(CartPageActivity.this,response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(CartPageActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<GetCartResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartPageActivity.this, "Failed to fetch", Toast.LENGTH_LONG).show();
            }

        });


        // public void deleteItem(final int position) {
        //   final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
//      //  Call<Boolean> call = mIProductAPI.deleteCart(productlist.get(position).getCartid(),productlist.get(position).getProductId(),productlist.get(position).getMid());
//       // call.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                if (response.code() == 200 && response.body() == true) {
//                    productlist.remove(position);
//                    cartAdapter.notifyItemRemoved(position);
//                    progressDialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(CartPageActivity.this, "Failed to Delete", Toast.LENGTH_LONG);
//            }
//        });
//    }

//    @Override
//    public void delete(int position) {
//
//    }
    }

    @Override
    public void delete(final int position, Long cartId, Long prId, Long merchantId) {

        DelRequestModel delRequestModel = new DelRequestModel(cartId, prId, merchantId);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<DelResponseModel> call = mIProductAPI1.deleteCart(delRequestModel);
        call.enqueue(new Callback<DelResponseModel>() {
            @Override
            public void onResponse(Call<DelResponseModel> call, Response<DelResponseModel> response) {
                if (response.code() == 200 && response.body().getSuccess()) {
                    productlist.remove(position);
                    cartAdapter.notifyItemRemoved(position);
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(CartPageActivity.this, response.body().getReason() + "", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<DelResponseModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CartPageActivity.this, "Failed to Delete", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuresource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            //TODO handle click
            return true;
        }
        else if(id== R.id.action_home) {
            Intent displayByCategory = new Intent(CartPageActivity.this, DisplayByCategory.class);
            startActivity(displayByCategory);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}