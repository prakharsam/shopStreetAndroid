package com.example.coviam.myapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.coviam.myapp.Adapter.MerchantAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merchant_recycler_view);
        merchantlist = new ArrayList<>();
        merchantAdapter = new MerchantAdapter(merchantlist, this);
        projectApi = LoginController.getInstance().getProductClient().create(ProjectAPI.class);
        productID = getIntent().getLongExtra("productID", 0);


        Call<List<MerchantDto>> userCall1 = projectApi.getMerchantsByPid(productID);
        userCall1.enqueue(new Callback<List<MerchantDto>>() {
            @Override
            public void onResponse(Call<List<MerchantDto>> call, Response<List<MerchantDto>> response) {
                merchantlist.addAll(response.body());
                merchantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MerchantDto>> call, Throwable t) {
                Toast.makeText(MerchantActivity.this, "No merchants to display", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void addToCartFromAdapter() {
        //
    }
}
