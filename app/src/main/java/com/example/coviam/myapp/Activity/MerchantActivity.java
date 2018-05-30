package com.example.coviam.myapp.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.MerchantAdapter;
import com.example.coviam.myapp.Model.cart.CartData;
import com.example.coviam.myapp.Model.cart.CartResponseDTO;
import com.example.coviam.myapp.Model.merchant.MerchantDto;
import com.example.coviam.myapp.R;
import com.example.coviam.myapp.network.LoginController;
import com.example.coviam.myapp.network.ProjectAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MerchantActivity extends AppCompatActivity implements MerchantAdapter.IAdapterCommunicator {
    RecyclerView recyclerview;
    Long productID;
    MerchantAdapter merchantAdapter;
    List<MerchantDto> merchantlist;
    ProjectAPI projectApi;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_recycler_view);
        merchantlist = new ArrayList<>();
        merchantAdapter = new MerchantAdapter(merchantlist, this);

        recyclerview = findViewById(R.id.recycler_view_merchant);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview.setAdapter(merchantAdapter);
        projectApi = LoginController.getInstance().getProductClient().create(ProjectAPI.class);
        productID = getIntent().getLongExtra("productID", 0);
        alertDialog = new AlertDialog.Builder(MerchantActivity.this);


        Call<List<MerchantDto>> userCall1 = projectApi.getMerchantsByPid(productID);
        userCall1.enqueue(new Callback<List<MerchantDto>>() {
            @Override
            public void onResponse(Call<List<MerchantDto>> call, Response<List<MerchantDto>> response) {
                if (null != response.body()) {
                    merchantlist.addAll(response.body());
                    merchantAdapter.notifyDataSetChanged();
                } else {
                    alertDialog.setTitle("OOps!!");
                    alertDialog.setMessage("No merchants For this product");
                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<List<MerchantDto>> call, Throwable t) {
                alertDialog.setTitle("OOps!!");
                alertDialog.setMessage("Something Went Wrong .Try after sometime");
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                alertDialog.show();
            }
        });
    }

    @Override
    public void addToCartFromAdapter() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();

        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        Long userid = preferences.getLong("id", 0);


        Long pid = getIntent().getLongExtra("productID", 0L);
        String quantity = getIntent().getStringExtra("qty");
        Long mid = getIntent().getLongExtra("merchantId", 0L);
        Double price = getIntent().getDoubleExtra("price", 0);

        Long qty = 0L;
        if (quantity != null && !quantity.isEmpty()) {
            qty = Long.parseLong(quantity);
        } else {
            qty = 1L;
        }

        ProjectAPI projectApi = LoginController.getInstance().getClient().create(ProjectAPI.class);

        Call<CartResponseDTO> call = projectApi.addToCartApi(new CartData(userid, pid, qty, mid, price));
        call.enqueue(new Callback<CartResponseDTO>() {
            @Override
            public void onResponse(Call<CartResponseDTO> call, Response<CartResponseDTO> response) {
                if (response.body() != null && response.body().getSuccess()) {
                    progressDialog.dismiss();
                    Toast.makeText(MerchantActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MerchantActivity.this, "Failed To Add To Cart", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CartResponseDTO> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
